package sys.dao;

import org.hibernate.Session;
import sys.model.Factura;

/**
 *
 * @author lucodeveloper
 */

public interface facturaDao {
    
    public Factura obtenerUltimoRegistro(Session session) throws Exception;
    
    public Long obtenerTotalRegistrosEnFactura(Session session);
    
    public boolean guardarVentaFactura(Session session, Factura factura) throws Exception;
    
}
