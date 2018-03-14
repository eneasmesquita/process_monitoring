package aut.com.dao;

import aut.com.login.ACryptLogin;
import aut.com.login.Usuario;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author eneas
 */
public class UsuarioDao {

    private Connection conn;

    public void preparaValidaLogin() {
        try {
            String home = System.getProperty("user.home");

            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            URL url = new URL("http://intranet/keystore/gti_desenv");
            URLConnection urlc = url.openConnection();

            bis = new BufferedInputStream(urlc.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(home + "/gti_desenv"));

            int i;
            while ((i = bis.read()) != -1) {
                bos.write(i);
            }

            bis.close();
            bos.close();

            System.setProperty("javax.net.ssl.trustStore", home + "/gti_desenv");
            System.setProperty("javax.net.ssl.trustStorePassword", "000000");
        } catch (Throwable t) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, t);
        }
    }

    public boolean validaPorSenhaBrma(String login, String senha) {
        try {
            String dados = "acao=login&CRYPT=1&USERNAME=" + new ACryptLogin().encrypt(login) + "&PASSWORD=" + new ACryptLogin().encrypt(senha);
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) sslsocketfactory.createSocket("000.00.00.000", 000);
            socket.setKeepAlive(true);
            socket.setEnableSessionCreation(true);
            socket.setUseClientMode(true);
            socket.setWantClientAuth(true);
            String path = "/cgi/loginaux.cgi";
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1"));
            wr.write("POST " + path + " HTTP/1.0\r\n");
            wr.write("Content-Length: " + dados.length() + "\r\n");
            wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
            wr.write("\r\n");

            try {
                wr.write(dados);
                wr.flush();
            } catch (Throwable ex) {
                //ex.printStackTrace();
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ISO-8859-1"));
            String str = "";
            String line;
            while ((line = rd.readLine()) != null) {
                str = str + line;
            }

            if (str.contains("Senha Inválida")) {
                System.out.println("*** rdiWeb: Usuário [" + login + "] não logou, Senha [BRMA] Inválida!!!");
                return false;
            } else {
                System.out.println("*** rdiWeb: Usuário [" + login + "] logado [BRMA].");
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean validaPorSenhaIntranet() {
        return false;
    }

    public boolean validaLogin(String login, String senha) throws FileNotFoundException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, KeyManagementException, ClassNotFoundException {
        conn = new ConexaoDB().conectar();
        boolean retorno = false;

        try {
            String sql = "SELECT brma, matricula, nome_completo, senha, md5(?)::varchar as senhamd5 "
                    + "FROM intranet.usuario WHERE username=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, senha);
            stmt.setString(2, login);
            ResultSet rs1 = stmt.executeQuery();

            if (rs1.next()) {
                Boolean brma = false;
               
                if (brma) {
                    retorno = validaPorSenhaBrma(login, senha);
                } else {
                    if (rs1.getString(4).equals(rs1.getString(5))) {
                        System.out.println("*** rdiWeb: Usuário [" + login + "] logado [senha intranet].");
                        retorno = true;
                    } else {
                        System.out.println("*** rdiWeb: Usuário [" + login + "] não logou, Senha [intranet] Inválida!!!");
                        retorno = false;
                    }
                }
            } else {
                System.out.println("*** rdiWeb: Usuário [" + login + "] não logou. Usuário não existe!");
                retorno = false;
            }
            rs1.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return retorno;
    }

    public void registraAcesso(String username, int sistema, String ip) {
        if (username != null && !username.equals("")) {
            conn = new ConexaoDB().conectar();
            try {
                conn.setAutoCommit(true);
                String sql = "INSERT INTO intranet.sistemas_acessos VALUES (?, ?, now(), ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, sistema);
                stmt.setString(2, username);
                stmt.setString(3, ip);
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public Usuario autenticarUsuario(String login, String senha) {
        Usuario usu = null;
        System.out.println("login: " + login);
        try {
            if (validaLogin(login, senha)) {
                conn = new ConexaoDB().conectar();
                String sql = "SELECT matricula, nome_completo, lpad(matricula::varchar, 7, '0')::char(7) as chapa, brma, flag_senha, setor_nome, setor_id "
                        + "FROM intranet.view_usuario_sisacompo WHERE username=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, login);

                ResultSet rs1 = stmt.executeQuery();
                if (rs1.next()) {
                    usu = new Usuario();
                    usu.setLogin(login);
                    usu.setMatricula(rs1.getString(1));
                    usu.setNome(rs1.getString(2));
                    usu.setChapa(rs1.getString(3));
                    usu.setBrma(rs1.getBoolean(4));
                    usu.setFlagSenha(rs1.getBoolean(5));
                    usu.setSetor_nome(rs1.getString(6));
                    usu.setSetor_id(rs1.getInt(7));

                    //acessos do usuario
                    sql = "SELECT id_sistema FROM intranet.usuario_sistema WHERE acesso AND username=? ORDER BY 1";
                    PreparedStatement stmt1 = conn.prepareStatement(sql);
                    stmt1.setString(1, login);
                    ResultSet rs2 = stmt1.executeQuery();
                    List<String> acessos = new ArrayList<String>();
                    while (rs2.next()) {
                        acessos.add(rs2.getString(1));
                    }
                    usu.setAcessos(acessos);
                    rs2.close();
                    stmt1.close();
                    if (usu.isBrma()) {
                        sql = "UPDATE intranet.usuario SET senha=md5(?) WHERE username=?";
                        PreparedStatement stmt2 = conn.prepareStatement(sql);
                        stmt2.setString(1, senha);
                        stmt2.setString(2, login);
                        stmt2.executeUpdate();
                        stmt2.close();
                    }
                    System.out.println("*** SisaCompo: " + usu.toString());
                }
                rs1.close();
                stmt.close();
                } else {
                    System.out.println("*** SisaCompo: Usuario [" + login + "] nao logou!!!");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return usu;
    }
}
