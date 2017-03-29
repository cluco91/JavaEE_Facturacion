/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys.bean;

import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import sys.dao.clienteDao;
import sys.imp.clienteDaoImp;
import sys.model.Cliente;

/**
 *
 * @author lucodeveloper
 */

@Named(value = "clienteBean")
@ViewScoped
public class clienteBean {

    private List<Cliente> listaClientes;
    private Cliente cliente;
    
    public clienteBean() {
    }
    
    public void setListaClientes(List<Cliente> listaClientes){
        this.listaClientes = listaClientes;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public List<Cliente> getListaClientes(){
        clienteDao cDao = new clienteDaoImp();
        listaClientes = cDao.listarClientes();
        return listaClientes;
    }
    
    public void prepararNuevoCliente(){
        cliente = new Cliente();
    }
    
    public void nuevoCliente(){
        clienteDao cDao = new clienteDaoImp();
        cDao.newCliente(cliente);
    }
    
    public void modificarCliente(){
        clienteDao cDao = new clienteDaoImp();
        cDao.updateCliente(cliente);
        cliente = new Cliente();
    }
    
    public void eliminarCliente(){
        clienteDao cDao = new clienteDaoImp();
        cDao.deleteCliente(cliente);
        cliente = new Cliente();
    }
    
}
