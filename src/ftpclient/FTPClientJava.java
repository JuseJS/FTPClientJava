/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ftpclient;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPClientJava {

    public static void main(String[] args) {
        String server = "ftp.rediris.es";
        int port = 21;

        FTPClient ftpClient = new FTPClient();

        try {
            // Conectar al servidor FTP
            ftpClient.connect(server, port);

            // Verificar el código de respuesta después de la conexión
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Error: No se pudo conectar al servidor FTP.");
                ftpClient.disconnect();
                System.exit(1);
            }

            // Iniciar sesión como usuario anónimo
            boolean success = ftpClient.login("anonymous", "");
            if (!success) {
                System.out.println("Error: No se pudo iniciar sesión en el servidor FTP.");
                ftpClient.disconnect();
                System.exit(1);
            }

            // Obtener listado del directorio raíz
            System.out.println("Listado del directorio raíz de " + server + ":");
            System.out.println("----------------------------------------");

            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                String details = String.format("%s%-20s %,d bytes",
                        file.isDirectory() ? "d" : "-",
                        file.getName(),
                        file.getSize());
                System.out.println(details);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
