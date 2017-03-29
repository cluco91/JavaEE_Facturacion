package sys.dao;

import org.hibernate.Session;
import sys.model.Detallefactura;

/**
 *
 * @author lucodeveloper
 */

public interface detalleFacturaDao {
    
    public boolean guardarVentaDetalleFactura(Session session, Detallefactura detallefactura) throws Exception;
    
    
}
