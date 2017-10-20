/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.uesocc.ingenieria.prn335_2017.web.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.acceso.MetaFacadeLocal;
import sv.edu.uesocc.ingenieria.prn335_2017.datos.definiciones.Meta;

/**
 *
 * @author luis
 */
@Named(value = "frmMeta")
@ViewScoped
public class frmMeta implements Serializable {

    /**
     * Creates a new instance of frmMeta
     */
    public frmMeta() {
    }

    @EJB
    private MetaFacadeLocal mfl;
    private LazyDataModel<Meta> modelo;
    private Meta registro;
    private boolean btnadd = true;
    private boolean botones = false;
    private boolean seleccions = false;

    @PostConstruct
    private void inicio() {

        registro = new Meta();

        try {
            this.modelo = new LazyDataModel<Meta>() {
                @Override
                public Object getRowKey(Meta object) {
                    if (object != null) {
                        return object.getIdMeta();
                    }
                    return null;
                }

                @Override
                public Meta getRowData(String rowKey) {
                    if (rowKey != null && !rowKey.isEmpty() && this.getWrappedData() != null) {
                        try {
                            Integer buscado = new Integer(rowKey);
                            for (Meta reg : (List<Meta>) getWrappedData()) {
                                if (reg.getIdMeta().compareTo(buscado) == 0) {
                                    return reg;
                                }
                            }
                        } catch (Exception e) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                        }
                    }
                    return null;
                }

                @Override
                public List<Meta> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    List<Meta> salida = new ArrayList();
                    try {
                        if (mfl != null) {
                            this.setRowCount(mfl.count());
                            salida = mfl.findRange(first, pageSize);

                        }
                    } catch (Exception e) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                    }
                    return salida;
                }

            };
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * metodo para guardar los registros ingresados por el usuario
     */
    public void guardarRegistro() {
        try {
            if (this.registro != null && this.mfl != null) {
                if (this.mfl.create(registro)) {
                    System.out.println("SE HA AGREGADO CON EXITO!! YA PODEMOS!!");
                    inicio();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR NO SE AGREGO NADA " + e);
        }
    }

    /**
     * metodo para eliminar un registro de la tabla selecionado
     */
    public void Eliminar() {
        try {

            if (this.registro != null && this.mfl != null) {
                if (this.mfl.remove(registro)) {
                    this.registro = new Meta();
                    this.botones = false;
                    this.btnadd = true;
                    inicio();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * metodo para modificar un dato ya ingresado, que se encuentre en la tabla
     */
    public void Modificar() {
        try {
            if (this.registro != null && this.mfl != null) {
                if (this.mfl.edit(registro)) {
                    this.botones = false;
                    this.btnadd = true;
                    inicio();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * metodo que borra lo escrito en el formulario por el usuario
     */
    public void cancelar() {
        this.registro = new Meta();
        this.botones = false;
        this.btnadd = true;
    }

    /**
     * metodo para cambiar los botones, esconde o muestra los botones de
     * eliminar,modificar,crear y cancelar
     */
    public void cambiarSeleccion() {
        this.botones = true;
        this.btnadd = false;
    }

    /**
     * manda a llamar los datos de la variable
     *
     * @return
     */
    public LazyDataModel<Meta> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Meta> modelo) {
        this.modelo = modelo;
    }

    /**
     * manda a llamar los datos de la variable
     *
     * @return
     */
    public Meta getRegistro() {
        return registro;
    }

    /**
     * se le asigna los valores a la variable
     *
     * @param metas
     */
    public void setRegistro(Meta registro) {
        this.registro = registro;
    }

    public boolean isBtnadd() {
        return btnadd;
    }

    /**
     * se le asigna los valores a la variable
     *
     * @param metas
     */
    public void setBtnadd(boolean btnadd) {
        this.btnadd = btnadd;
    }

    public boolean isBotones() {
        return botones;
    }

    /**
     * se le asigna los valores a la variable
     *
     * @param metas
     */
    public void setBotones(boolean botones) {
        this.botones = botones;
    }

    public boolean isSeleccions() {
        return seleccions;
    }

    /**
     * se le asigna los valores a la variable
     *
     * @param metas
     */
    public void setSeleccions(boolean seleccions) {
        this.seleccions = seleccions;
    }

}
