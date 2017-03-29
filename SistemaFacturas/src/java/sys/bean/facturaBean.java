/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys.bean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import sys.clasesAuxiliares.reporteFactura;
import sys.dao.clienteDao;
import sys.dao.detalleFacturaDao;
import sys.dao.facturaDao;
import sys.dao.productoDao;
import sys.imp.clienteDaoImp;
import sys.imp.detalleFacturaDaoImp;
import sys.imp.facturaDaoImp;
import sys.imp.productoDaoImp;
import sys.model.Cliente;
import sys.model.Detallefactura;
import sys.model.Factura;
import sys.model.Producto;
import sys.model.Vendedor;
import sys.util.HibernateUtil;

/**
 *
 * @author lucodeveloper
 */

@Named(value = "facturaBean")
@ViewScoped
public class facturaBean {

    /**
     * Creates a new instance of facturaBean
     */
    Session session = null;
    Transaction transaction = null;
    
    @ManagedProperty("#{loginBean}")
    private loginBean lBean;

    private Cliente cliente;
    private Integer codigoCliente;

    private Producto producto;
    private String codigoBarra;

    private List<Detallefactura> listaDetalleFactura;

    private String cantidadProducto;
    private String productoSeleccionado;
    private Factura factura;

    private String cantidadProducto2;
    
    private Long numeroFactura;
    
    private BigDecimal totalVentaFactura;
    
    private Vendedor vendedor;

    public facturaBean() {
        this.factura = new Factura();
        this.listaDetalleFactura = new ArrayList<>();
        this.vendedor = new Vendedor();
        this.cliente = new Cliente();
    }

    public loginBean getlBean() {
        return lBean;
    }

    public void setlBean(loginBean lBean) {
        this.lBean = lBean;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Integer codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public List<Detallefactura> getListaDetalleFactura() {
        return listaDetalleFactura;
    }

    public void setListaDetalleFactura(List<Detallefactura> listaDetalleFactura) {
        this.listaDetalleFactura = listaDetalleFactura;
    }

    public String getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(String cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public String getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(String productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getCantidadProducto2() {
        return cantidadProducto2;
    }

    public void setCantidadProducto2(String cantidadProducto2) {
        this.cantidadProducto2 = cantidadProducto2;
    }

    public Long getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Long numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public BigDecimal getTotalVentaFactura() {
        return totalVentaFactura;
    }

    public void setTotalVentaFactura(BigDecimal totalVentaFactura) {
        this.totalVentaFactura = totalVentaFactura;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }
    
    
    
    

    public void agregarDatosCliente(Integer codCliente) {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            clienteDao cDao = new clienteDaoImp();
            this.transaction = this.session.beginTransaction();

            this.cliente = cDao.obtenerClientePorCodigo(this.session, codCliente);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del Cliente han sido Agregados."));
        } catch (Exception e) {
            if (this.transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void agregarDatosCliente2() {
        this.session = null;
        this.transaction = null;

        try {

            if (this.codigoCliente == null) {
                return;
            }

            this.session = HibernateUtil.getSessionFactory().openSession();
            clienteDao cDao = new clienteDaoImp();
            this.transaction = this.session.beginTransaction();

            this.cliente = cDao.obtenerClientePorCodigo(this.session, this.codigoCliente);

            if (this.cliente != null) {
                this.codigoCliente = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del Cliente han sido Agregados."));
            } else {
                this.codigoCliente = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cliente no encontrado."));
            }

            this.transaction.commit();

        } catch (Exception e) {
            if (this.transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void pedirCantidadProducto(String codBarra) {
        this.productoSeleccionado = codBarra;
    }

    public void agregarDatosProducto() {
        this.session = null;
        this.transaction = null;

        try {

            if (!(this.cantidadProducto.matches("[0-9]*")) || this.cantidadProducto.equals("0") || this.cantidadProducto.equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta."));
                this.cantidadProducto = "";
            } else {

                this.session = HibernateUtil.getSessionFactory().openSession();
                productoDao pDao = new productoDaoImp();

                this.transaction = this.session.beginTransaction();

                this.producto = pDao.obtenerProductoPorCodBarra(this.session, this.productoSeleccionado);

                this.listaDetalleFactura.add(new Detallefactura(null, null, this.producto.getCodBarra(),
                        this.producto.getNombreProducto(), Integer.parseInt(this.cantidadProducto), this.producto.getPrecioVenta(),
                        BigDecimal.valueOf(Integer.parseInt(this.cantidadProducto) * this.producto.getPrecioVenta().floatValue())));

                this.transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del Producto han sido Agregados al Detalle."));

                this.cantidadProducto = "";
                this.calcularTotalFactura();

            }

        } catch (Exception e) {
            if (this.transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void mostrarCantidadProducto2() {

        this.session = null;
        this.transaction = null;

        try {

            if (this.codigoCliente.equals("")) {
                return;
            }

            this.session = HibernateUtil.getSessionFactory().openSession();
            productoDao pDao = new productoDaoImp();
            this.transaction = this.session.beginTransaction();

            this.producto = pDao.obtenerProductoPorCodBarra(this.session, codigoBarra);

            if (this.producto != null) {

                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dialogCantidadProducto2').show();");

                this.codigoBarra = null;

            } else {
                this.codigoBarra = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Producto no encontrado."));

            }

            this.transaction.commit();

        } catch (Exception e) {
            if (this.transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }

    }

    public void agregarDatosProducto2() {

        if (!(this.cantidadProducto2.matches("[0-9]*")) || this.cantidadProducto2.equals("0") || this.cantidadProducto2.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta."));
            this.cantidadProducto2 = "";
        } else {

            this.listaDetalleFactura.add(new Detallefactura(null, null, this.producto.getCodBarra(), this.producto.getNombreProducto(),
                    Integer.parseInt(this.cantidadProducto2), this.producto.getPrecioVenta(),
                    BigDecimal.valueOf(Integer.parseInt(this.cantidadProducto2) * this.producto.getPrecioVenta().floatValue())));

            this.cantidadProducto2 = "";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto Agregado al Detalle."));

            this.calcularTotalFactura();
        }
    }

    public void calcularTotalFactura() {
        this.totalVentaFactura = new BigDecimal("0");

        try {
            for (Detallefactura item : listaDetalleFactura) {
                BigDecimal totalVentaPorProducto = item.getPrecioVenta().multiply(new BigDecimal(item.getCantidad()));
                item.setTotal(totalVentaPorProducto);
                totalVentaFactura = totalVentaFactura.add(totalVentaPorProducto);
            }

            this.factura.setTotalVenta(totalVentaFactura);
            System.out.println("Total a Vender: " + this.factura.getTotalVenta());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", e.getMessage()));
        }
    }
    
    public void quitarProductoDetalleFactura(String codBarra, Integer filaSeleccionada){
        try {
            int i = 0;
            for(Detallefactura item : this.listaDetalleFactura){
                if (item.getCodBarra().equals(codBarra) && filaSeleccionada.equals(i)){
                    this.listaDetalleFactura.remove(i);
                    break;
                }
                i++;
                
            }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Se ha retirado el producto de la factura"));
            this.calcularTotalFactura();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", e.getMessage()));
        }
 
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Se Modificó la Cantidad."));
        this.calcularTotalFactura();
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "No se hizo ningun cambio."));
    }
    
    public void numeracionFactura(){
        this.session = null;
        this.transaction = null;
        
        try {
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();
            facturaDao fDao = new facturaDaoImp();
            
            this.numeroFactura = fDao.obtenerTotalRegistrosEnFactura(this.session);
            
            if(this.numeroFactura <=0 || this.numeroFactura==null){
                this.numeroFactura = Long.valueOf("1");
            }else{
                this.factura = fDao.obtenerUltimoRegistro(this.session);
                this.numeroFactura = Long.valueOf(this.factura.getNumeroFactura()+1);
                
                this.totalVentaFactura = new BigDecimal("0");
            }
            
            this.transaction.commit();
        } catch (Exception e) {
            if(this.transaction != null){
                this.transaction.rollback();
            }
            System.out.println(e.getMessage());
        } finally{
            if (this.session != null){
                this.session.close();
            }
        }
                
    }
    
    public void limpiarFactura(){
        this.cliente = new Cliente();
        this.factura = new Factura();
        this.listaDetalleFactura = new ArrayList<>();
        this.numeroFactura = null;
        this.totalVentaFactura = null;
        
        this.disableButton();
    }
    
    public void guardarVenta(){
        this.session = null;
        this.transaction = null;
        this.vendedor.setCodVendedor(lBean.getUsuario().getVendedor().getCodVendedor());
        
        try {
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            productoDao pDao = new productoDaoImp();
            facturaDao fDao = new facturaDaoImp();
            detalleFacturaDao dFDao = new detalleFacturaDaoImp();
            
            this.transaction = this.session.beginTransaction();
            
            this.factura.setNumeroFactura(this.numeroFactura);
            this.factura.setCliente(this.cliente);
            this.factura.setVendedor(this.vendedor);
            
            fDao.guardarVentaFactura(this.session, this.factura);
            
            this.factura = fDao.obtenerUltimoRegistro(this.session);
            
            for(Detallefactura item : listaDetalleFactura){
                this.producto = pDao.obtenerProductoPorCodBarra(this.session, item.getCodBarra());
                item.setFactura(this.factura);
                item.setProducto(this.producto);
                
                dFDao.guardarVentaDetalleFactura(this.session, item);
            }
            
            this.transaction.commit();   
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Venta Registrada."));
            
            this.limpiarFactura();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(this.transaction != null){
                this.transaction.rollback();
            }
        } finally{
            if (this.session != null){
                this.session.close();
            }
        }
    }
    
    //Activar y Desactivar controles en la factura
    private boolean enabled;
    
    public boolean isEnabled(){
        return enabled;
    }
    
    public void enableButton(){
        enabled = true;
    }
    
    public void disableButton(){
        enabled = false;
    }
    
    private String fechaSistema;
    
    public String getFechaSistema(){
        
        Calendar fecha = new GregorianCalendar();
        
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        
        this.fechaSistema = (dia + "/" + (mes+1) + "/" + anio);
        
        return fechaSistema;
    }
    
    public void verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        this.vendedor.setCodVendedor(lBean.getUsuario().getVendedor().getCodVendedor());
        int cc = this.cliente.getCodCliente();
        int cv = this.vendedor.getCodVendedor();
        int cf = this.factura.getCodFactura() + 1;        

        //invocamos al metodo guardarVenta, para almacenar la venta en las tablas correspondientes
        this.guardarVenta();

        //Instancia hacia la clase reporteFactura        
        reporteFactura rFactura = new reporteFactura();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/Reportes/factura.jasper");

        System.out.println("Cliente: " + cc);
        System.out.println("Vendedor: " + cv);
        System.out.println("Factura: " + cf);

        rFactura.getReporte(ruta, cc, cv, cf);
        FacesContext.getCurrentInstance().responseComplete();
               
    }

}
