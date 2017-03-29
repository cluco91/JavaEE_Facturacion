package sys.imp;

import org.hibernate.Session;
import sys.dao.detalleFacturaDao;
import sys.model.Detallefactura;

/**
 *
 * @author lucodeveloper
 */

public class detalleFacturaDaoImp implements detalleFacturaDao {

    @Override
    public boolean guardarVentaDetalleFactura(Session session, Detallefactura detallefactura) throws Exception {
        session.save(detallefactura);
        return true;
    }
    
}
