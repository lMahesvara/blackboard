
package interfaces;

public interface IServer {
    
    public void conexion();
    public void sendResponse();
    public void ejecutarNuevoHilo(String json);
    
}
