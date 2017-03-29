package sys.dao;

import java.util.List;
import sys.model.Vendedor;

/**
 *
 * @author lucodeveloper
 */

public interface vendedorDao {
    
    public List<Vendedor> listarVendedor();
    public void newVendedor(Vendedor cliente);
    public void updateVendedor(Vendedor cliente);
    public void deleteVendedor(Vendedor cliente);
    
}
