/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys.bean;

import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import sys.dao.vendedorDao;
import sys.imp.vendedorDaoImp;
import sys.model.Vendedor;

/**
 *
 * @author lucodeveloper
 */

@Named(value = "vendedorBean")
@ViewScoped
public class vendedorBean {

    /**
     * Creates a new instance of vendedorBean
     */
    private List<Vendedor> listaVendedores;
    private Vendedor vendedor;
    
    public vendedorBean() {
        vendedor = new Vendedor();
    }

    public void setListaVendedores(List<Vendedor> listaVendedores) {
        this.listaVendedores = listaVendedores;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }
    
    public List<Vendedor> getListaVendedores() {
        vendedorDao vDao = new vendedorDaoImp();
        listaVendedores = vDao.listarVendedor();
        return listaVendedores;
    }
    
    public void prepararNuevoVendedor(){
        vendedor = new Vendedor();
    }
    
    public void nuevoVendedor(){
        vendedorDao vDao = new vendedorDaoImp();
        vDao.newVendedor(vendedor);
    }
    
    public void modificarVendedor(){
        vendedorDao vDao = new vendedorDaoImp();
        vDao.updateVendedor(vendedor);
        vendedor = new Vendedor();
    }
    
    public void eliminarVendedor(){
        vendedorDao vDao = new vendedorDaoImp();
        vDao.deleteVendedor(vendedor);
        vendedor = new Vendedor();
    }
    
}
