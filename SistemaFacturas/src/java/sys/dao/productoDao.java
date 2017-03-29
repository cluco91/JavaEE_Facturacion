package sys.dao;

import java.util.List;
import org.hibernate.Session;
import sys.model.Producto;

/**
 *
 * @author lucodeveloper
 */

public interface productoDao {
    
    public List<Producto> listarProductos();
    public void newProducto(Producto producto);
    public void updateProducto(Producto producto);
    public void deleteProducto(Producto producto);
    
    public Producto obtenerProductoPorCodBarra(Session session, String codBarra) throws Exception;
}
