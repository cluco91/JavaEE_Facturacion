package sys.dao;

import sys.model.Usuario;

/**
 *
 * @author lucodeveloper
 */

public interface usuarioDao {
    
    public Usuario obtenerDatosPorUsuario(Usuario usuario);
    public Usuario login(Usuario usuario);
    
}
