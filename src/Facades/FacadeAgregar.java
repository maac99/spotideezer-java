/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import Controladores.AlbumJpaController;
import Controladores.AuditoriaJpaController;
import Controladores.CancionJpaController;
import Controladores.CancionxlistasrJpaController;
import Controladores.EmpresaJpaController;
import Controladores.GeneroxidiomaJpaController;
import Controladores.InterpreteJpaController;
import Controladores.ListaRJpaController;
import Controladores.PaisJpaController;
import Controladores.SuscripcionJpaController;
import Controladores.UsuarioJpaController;
import Entidades.Album;
import Entidades.Auditoria;
import Entidades.Cancion;
import Entidades.CancionAux;
import Entidades.CancionPK;
import Entidades.Cancionxlistasr;
import Entidades.CancionxlistasrPK;
import Entidades.Cxi;
import Entidades.Empresa;
import Entidades.Generoxidioma;
import Entidades.Individual;
import Entidades.Interprete;
import Entidades.ListaR;
import Entidades.ListaRPK;
import Entidades.Pais;
import Entidades.Registro;
import Entidades.RegistroPK;
import Entidades.Suscripcion;
import Entidades.Usuario;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Sala BD
 */
public class FacadeAgregar {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoBDPU");
    private EntityManager em = emf.createEntityManager();
    private Usuario user;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void MostrarMensajeAdvertencia(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR, mensaje, ButtonType.OK);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    public void MostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(AlertType.CONFIRMATION, mensaje, ButtonType.OK);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    public List<Pais> darListaDePaises() {

        PaisJpaController controladorPais = new PaisJpaController(this.emf);
        List<Pais> listaPaises = controladorPais.findPaisEntities();
        return listaPaises;
    }

    public List<Interprete> darListaDeInterpretes() {
        InterpreteJpaController controladorInterprete = new InterpreteJpaController(this.emf);
        List<Interprete> listaInterprete = controladorInterprete.findInterpreteEntities();

        return listaInterprete;
    }

    public int darNumeroSuscripciones() {
        SuscripcionJpaController controladorSuscripcion = new SuscripcionJpaController(this.emf);
        int result = controladorSuscripcion.getSuscripcionCount();
        return result;
    }

    public List<Usuario> darListaDeUsuarios() {
        UsuarioJpaController controladorUsuario = new UsuarioJpaController(this.emf);
        List<Usuario> listaUsuarios = controladorUsuario.findUsuarioEntities();
        return listaUsuarios;
    }

    public int darNumeroListasR() {
        ListaRJpaController controladorListaR = new ListaRJpaController(this.emf);
        int result = controladorListaR.getListaRCount();
        return result;
    }

    public List<ListaR> darListasR() {
        ListaRJpaController controladorListaR = new ListaRJpaController(this.emf);
        List<ListaR> result = controladorListaR.findListaREntities();
        return result;
    }

    public List<ListaR> darListasRUsuario() {

        ListaRJpaController controladorListaR = new ListaRJpaController(this.emf);
        List<ListaR> result = controladorListaR.findListaREntities();
        List<ListaR> result2 = new ArrayList<>();
        EntityTransaction tx = this.em.getTransaction();
        tx.begin();
        for (ListaR listaR : result) {

            if (listaR.getListaRPK().getUsuarioNickname().equals(this.user.getNickname())) {

                result2.add(listaR);

            }
        }
        tx.commit();

        return result2;
    }

    public boolean existeListaR(String name, List<ListaR> listica) {
        boolean existe = false;

        for (ListaR listaR : listica) {
            if (listaR.getNombreLdr().equals(name)) {
                existe = true;
                break;
            }
        }

        return existe;
    }

    public long darIdInterprete(String nr, String na, List<Interprete> listica) {
        long id = 0;

        for (Interprete interprete : listica) {
            if (interprete.getNombreArtistico().equals(na) && interprete.getNombreReal().equals(nr)) {
                id = interprete.getInterpreteIdIn();
                break;
            }
        }

        return id;
    }

    public List<Cancionxlistasr> darCancionxListaR() {
        CancionxlistasrJpaController controladorCxLr = new CancionxlistasrJpaController(this.emf);
        List<Cancionxlistasr> result = controladorCxLr.findCancionxlistasrEntities();
        return result;
    }

    public int numeroCancionesListaR(List<Cancionxlistasr> listica, long IdListr) {
        int num = 0;
        for (Cancionxlistasr cancionxlistasr : listica) {
            if (cancionxlistasr.getCancionxlistasrPK().getListaRIdListar() == IdListr) {
                num++;
            }
        }
        return num;
    }

    public List<CancionAux> darCancionesDeListaRUsuario(String Nldr) {
        String nik = this.user.getNickname();
        Vector<Cancion> resultados = new Vector<Cancion>();
        List<CancionAux> ff = new ArrayList<CancionAux>();

        StringBuilder sql = new StringBuilder("select * "
                + "from cancion "
                + "where titulo_c in "
                + "(select cancion_titulo_c as titulo_c "
                + "from cancionxlistasr inner join lista_r on(lista_r_id_listar = id_listar) "
                + "where lista_r_nickname = '" + nik + "' and nombre_ldr = '" + Nldr + "')");

        Query query = this.em.createNativeQuery(sql.toString(), Cancion.class);
        resultados = (Vector<Cancion>) query.getResultList();

        Vector<ListaR> res = new Vector<ListaR>();

        StringBuilder sql2 = new StringBuilder("select * "
                + "from lista_r "
                + "where nombre_ldr = '" + Nldr + "' and usuario_nickname = '" + nik + "'");

        Query query2 = this.em.createNativeQuery(sql2.toString(), ListaR.class);
        res = (Vector<ListaR>) query2.getResultList();

        ListaR ll = res.get(0);

        //System.out.println(ll.getNombreLdr());
        for (Cancion resultado : resultados) {
            CancionAux jaja = new CancionAux();
            //System.out.println(resultado.getAlbumIdAlbum().getIdAlbum());
            Album a = this.em.find(Album.class, resultado.getAlbumIdAlbum().getIdAlbum());
            Interprete e = this.em.find(Interprete.class, resultado.getInterprete().getInterpreteIdIn());

            jaja.setTitulo_c(resultado.getCancionPK().getTituloC());
            jaja.setTitulo_alabum(a.getTituloAlbum());
            jaja.setNombre_real(e.getNombreReal());
            jaja.setNombre_artistico(e.getNombreArtistico());

            CancionxlistasrPK jj = new CancionxlistasrPK(jaja.getTitulo_c(), resultado.getInterprete().getInterpreteIdIn(), ll.getListaRPK().getIdListar(), nik);
            Cancionxlistasr es = this.em.find(Cancionxlistasr.class, jj);

            jaja.setOrden(es.getOrden().intValue());

            //System.out.println(jaja.getOrden());
            ff.add(jaja);
        }

        Collections.sort(ff);
        return ff;
    }

    public Interprete agregarAutor(String Nr, String Na, String p) {
        InterpreteJpaController controladorInterprete = new InterpreteJpaController(this.emf);

        Interprete NuevoIN = new Interprete();

        em.getTransaction().begin();
        Pais pp = em.find(Pais.class, p);
        //p.setNombrePais("Colombia");

        NuevoIN.setNombreArtistico(Na);
        NuevoIN.setNombreReal(Nr);
        NuevoIN.setPaisNombrePais(pp);

        List<Interprete> listaInterprete = this.darListaDeInterpretes();

        NuevoIN.setInterpreteIdIn(new Long(listaInterprete.size() + 100));

        em.persist(NuevoIN);
        em.getTransaction().commit();

        return NuevoIN;
    }

    public boolean verificarTarjeta(String numTarjeta) {
        boolean res = false;
        StringBuilder sql = new StringBuilder("select validarNumTarjeta(?) from dual ");
        Query query = this.em.createNativeQuery(sql.toString());
        query.setParameter(1, numTarjeta);
        BigDecimal resultados = (BigDecimal) query.getSingleResult();

        BigDecimal comparacion = new BigDecimal(1);

        if (resultados.compareTo(comparacion) == 0) {
            res = true;
        }

        return res;
    }

    public Usuario agregarUsuario(String nickname, String nombreUsuario, String apellido_usuario, String pais, String nombreSuscripcion) throws Exception {
        UsuarioJpaController usuarioControlador = new UsuarioJpaController(this.emf);
        Usuario usuario = new Usuario();

        int numSus = this.darNumeroSuscripciones();
        this.em.getTransaction().begin();

        usuario.setApellidoU(apellido_usuario);
        usuario.setNombreU(nombreUsuario);
        usuario.setNickname(nickname);
        Pais pp = this.em.find(Pais.class, pais);
        usuario.setPaisNombrePais(pp);

        if ((this.em.find(Usuario.class, usuario.getNickname())) == null) {

            this.em.persist(usuario);

            if ("individual".equals(nombreSuscripcion.toLowerCase())) {

                Suscripcion suscripcion = new Suscripcion();
                suscripcion.setSusTipoSus("IND");
                //System.out.println("VENECAAAAAASSS2");

                //suscripcion.setSusIdSuscripcion(new Long(listaSuscripciones.size()+1));
                suscripcion.setSusIdSuscripcion(new Long(numSus + 1));
                Date fechaActual = new Date();

                suscripcion.setSusFechaInicio(fechaActual);

                Date fechaSiguiente = sumarRestarAños(fechaActual, 1);
                suscripcion.setSusFechaUltimaRenovacion(fechaSiguiente);
                suscripcion.setUsuarioNickname(usuario);

                Individual individual = new Individual();
                individual.setSusIdSuscripcion(suscripcion.getSusIdSuscripcion());

                suscripcion.setIndividual(individual);

                this.em.persist(suscripcion);

                this.em.persist(individual);

            }

        } else {
            this.em.getTransaction().commit();
            throw new Exception("El usuario ya existe");
        }
        this.em.getTransaction().commit();
        return usuario;
    }

    public Date sumarRestarAños(Date fecha, int anos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(calendar.YEAR, anos);
        return calendar.getTime();
    }

    public List<CancionAux> buscarCanciones(String busqueda) throws Exception {
        Vector<Cancion> resultados = new Vector<Cancion>();
        List<CancionAux> ff = new ArrayList<CancionAux>();
        EntityTransaction tx = this.em.getTransaction();
        tx.begin();
        StringBuilder sql = new StringBuilder("select * "
                + "from cancion "
                + "where titulo_c in "
                + "(select cancion_titulo_c as titulo_c "
                + "from cxi inner join interprete using(interprete_id_in) "
                + "where cancion_titulo_c like '%" + busqueda + "%' or nombre_artistico like '%" + busqueda + "%')");

        Query query = this.em.createNativeQuery(sql.toString(), Cancion.class);
        resultados = (Vector<Cancion>) query.getResultList();
        if (!resultados.isEmpty()) {
            for (Cancion resultado : resultados) {
                CancionAux jaja = new CancionAux();
                //System.out.println(resultado.getAlbumIdAlbum().getIdAlbum());
                Album a = this.em.find(Album.class, resultado.getAlbumIdAlbum().getIdAlbum());
                Interprete e = this.em.find(Interprete.class, resultado.getInterprete().getInterpreteIdIn());

                jaja.setTitulo_c(resultado.getCancionPK().getTituloC());
                jaja.setTitulo_alabum(a.getTituloAlbum());
                jaja.setNombre_real(e.getNombreReal());
                jaja.setNombre_artistico(e.getNombreArtistico());

                ff.add(jaja);
            }
            tx.commit();

            return ff;
        } else {
            tx.commit();
            throw new Exception("No hay resultados de la busqueda");
        }
    }

    public ListaR agregarListaR(String nameL) throws Exception {
        String nik = this.user.getNickname();
        this.em.getTransaction().begin();
        ListaR nueva = new ListaR();
        ListaRPK ja = new ListaRPK(this.darNumeroListasR() * 10, nik);

        if ((this.em.find(ListaR.class, ja)) == null && !existeListaR(nameL, this.darListasR())) {

            if ((this.em.find(Usuario.class, nik)) != null) {

                nueva.setNombreLdr(nameL);
                nueva.setTipo("Publica"); //por defecto sera publica
                Usuario u = this.em.find(Usuario.class, nik);
                nueva.setUsuario(u);
                ListaRPK j = new ListaRPK(this.darNumeroListasR() * 10, nik);
                nueva.setListaRPK(j);

                this.em.persist(nueva);

                this.em.getTransaction().commit();

            } else {
                this.em.getTransaction().commit();
                throw new Exception("El usuario no existe");
            }

        } else {
            this.em.getTransaction().commit();
            throw new Exception("La lista de Reproduccion ya existe");
        }
        return nueva;
    }

    public String agragrCancionListaR(CancionAux cancion, ListaR listica) throws Exception {
        String agregada = "";

        EntityTransaction tx = this.em.getTransaction();
        tx.begin();

        long idI = darIdInterprete(cancion.getNombre_real(), cancion.getNombre_artistico(), this.darListaDeInterpretes());

        if (idI != 0) {
            Cancion c;
            CancionPK j = new CancionPK(cancion.getTitulo_c(), idI);
            c = this.em.find(Cancion.class, j);

            CancionxlistasrPK jn = new CancionxlistasrPK();
            jn.setCancionIdIn(c.getCancionPK().getInterpreteIdIn());
            jn.setCancionTituloC(c.getCancionPK().getTituloC());
            jn.setListaRIdListar(listica.getListaRPK().getIdListar());
            jn.setListaRNickname(listica.getListaRPK().getUsuarioNickname());

            Cancionxlistasr n = new Cancionxlistasr(jn);

            int orden = numeroCancionesListaR(this.darCancionxListaR(), listica.getListaRPK().getIdListar()) + 1;

            BigInteger we = new BigInteger(Integer.toString(orden));
            n.setOrden(we);

            if (this.em.find(Cancionxlistasr.class, jn) == null) {
                this.em.persist(n);
                tx.commit();
                //this.MostrarMensajeConfirmacion(""+this.em.getTransaction().isActive());
            } else {
                tx.commit();
                throw new Exception("Ya existe la cancion en la lista de reproduccion");
            }

            agregada = darTiempoNumCancionesLDR(listica);
        } else {
            tx.commit();
            throw new Exception("No se puede agregar la cancion a la lista de reproduccion");
        }
        //tx.commit();
        return agregada;
    }

    public int actualizarListaR(String Nldr, String nuevoNLDR, List<CancionAux> listita) {
        //boolean actualizado = false;
        int countUpdate2 = 0;
        this.em.getTransaction().begin();
        StringBuilder sql = new StringBuilder("update lista_r set Nombre_ldr='" + nuevoNLDR + "' where (nombre_ldr = '" + Nldr + "' and usuario_nickname = '" + this.getUser().getNickname() + "')");
        int countUpdate = this.em.createNativeQuery(sql.toString()).executeUpdate();
        //System.out.println("trcyhkgvypuioi5768");
        //System.out.println(countUpdate);

        Vector<ListaR> res = new Vector<ListaR>();

        StringBuilder sql3 = new StringBuilder("select * "
                + "from lista_r "
                + "where nombre_ldr = '" + nuevoNLDR + "' and usuario_nickname = '" + this.getUser().getNickname() + "'");

        Query query3 = this.em.createNativeQuery(sql3.toString(), ListaR.class);
        res = (Vector<ListaR>) query3.getResultList();

        ListaR ll = res.get(0);

        for (CancionAux object : listita) {
            StringBuilder sql2 = new StringBuilder("update cancionxlistasr set orden =" + object.getOrden() + "where (cancion_titulo_c = '" + object.getTitulo_c() + "' and lista_r_id_listar = '" + ll.getListaRPK().getIdListar() + "' and lista_r_nickname = '" + this.getUser().getNickname() + "')");
            countUpdate2 += this.em.createNativeQuery(sql2.toString()).executeUpdate();
        }

        this.em.getTransaction().commit();
        //System.out.println(countUpdate);

        return countUpdate2;
    }

    public List<Auditoria> darTablaAuditoria() {
        AuditoriaJpaController controladorAuditoria = new AuditoriaJpaController(this.emf);
        List<Auditoria> result = controladorAuditoria.findAuditoriaEntities();
        return result;
    }

    public String darTiempoNumCancionesLDR(ListaR listica) {
        String total = "";
        int cont = 0;
        int Tmins = 0;
        int Tsec = 0;
        int Thors = 0;
        List<Cancionxlistasr> todo = this.darCancionxListaR();
        List<Cancion> todoC = this.darCanciones();

        for (Cancionxlistasr cancion : todo) {
            // pr = cancion.getCancionxlistasrPK().getListaRIdListar();
            if (cancion.getCancionxlistasrPK().getListaRIdListar() == listica.getListaRPK().getIdListar()) {
                for (Cancion cancion1 : todoC) {
                    if (cancion1.getCancionPK().getTituloC().compareTo(cancion.getCancionxlistasrPK().getCancionTituloC()) == 0) {
                        cont++;
                        Date fc = cancion1.getDuracion();
                        long minuto = fc.getMinutes();
                        long segundo = fc.getSeconds();

                        Tmins += minuto;

                        Tsec += segundo;

                        if (Tsec >= 60) {
                            Tsec -= 60;
                            Tmins += 1;
                        }

                        if (Tmins >= 60) {
                            Tmins -= 60;
                            Thors += 1;
                        }

                        //System.out.println(fc);
                        //System.out.println(Thors+" "+Tmins+" "+Tsec);
                    }
                }
            }
        }
        total = "Numero total de canciones: " + cont + " y la duracion de la lista de reproduccion es: " + Thors + " Horas " + Tmins + " Minutos " + Tsec + " Segundos.";
        //System.out.println(total);
        return total;
    }

    public List<Cancion> darCanciones() {
        CancionJpaController controladorCancion = new CancionJpaController(this.emf);
        List<Cancion> result = controladorCancion.findCancionEntities();
        return result;
    }

    public void cerrarCocexion() {
        this.em.close();
    }

    public boolean existeReaccion(CancionAux cancion) {
        boolean existe = false;

        Vector<Cancion> resultados = new Vector<Cancion>();
        //List<CancionAux> ff = new ArrayList<CancionAux>();

        StringBuilder sql = new StringBuilder("select * "
                + "from cancion "
                + "where titulo_c = '" + cancion.getTitulo_c() + "'");

        Query query = this.em.createNativeQuery(sql.toString(), Cancion.class);
        resultados = (Vector<Cancion>) query.getResultList();

        Cancion f = resultados.get(0);

        RegistroPK rr = new RegistroPK(f.getCancionPK().getTituloC(), f.getCancionPK().getInterpreteIdIn(), this.user.getNickname(), "Like");

        Registro r = this.em.find(Registro.class, rr);

        if (r != null) {
            existe = true;
        }
        return existe;
    }

    public boolean actualizarReaccion(boolean actual, CancionAux cancionsita) {
        boolean realizado = false;

        Vector<Cancion> resultados = new Vector<Cancion>();
        //List<CancionAux> ff = new ArrayList<CancionAux>();

        StringBuilder sql = new StringBuilder("select * "
                + "from cancion "
                + "where titulo_c = '" + cancionsita.getTitulo_c() + "'");

        Query query = this.em.createNativeQuery(sql.toString(), Cancion.class);
        resultados = (Vector<Cancion>) query.getResultList();

        Cancion f = resultados.get(0);

        RegistroPK nueva = new RegistroPK(f.getCancionPK().getTituloC(), f.getCancionPK().getInterpreteIdIn(), this.user.getNickname(), "Like");

        Date nn = new Date();
        Registro insertar = new Registro(nueva, nn);

        if (!actual) {

            this.em.getTransaction().begin();
            this.em.persist(insertar);
            this.em.getTransaction().commit();
            realizado = true;

        } else {

            Registro borrar = em.find(Registro.class, nueva);

            this.em.getTransaction().begin();
            this.em.remove(borrar);
            this.em.getTransaction().commit();
            realizado = true;
        }

        return realizado;
    }

    public boolean registrarReproduccion(CancionAux cancionsita) {
        boolean can = false;
        if (!can) {
            Vector<Cancion> resultados = new Vector<Cancion>();
            //List<CancionAux> ff = new ArrayList<CancionAux>();

            StringBuilder sql = new StringBuilder("select * "
                    + "from cancion "
                    + "where titulo_c = '" + cancionsita.getTitulo_c() + "'");

            Query query = this.em.createNativeQuery(sql.toString(), Cancion.class);
            resultados = (Vector<Cancion>) query.getResultList();

            Cancion f = resultados.get(0);

            RegistroPK ff = new RegistroPK(f.getCancionPK().getTituloC(), f.getCancionPK().getInterpreteIdIn(), this.user.getNickname(), "Play");
            Date nn = new Date();
            Registro insertar = new Registro(ff, nn);
            this.em.getTransaction().begin();
            this.em.persist(insertar);
            this.em.getTransaction().commit();
            can = true;
        }

        return can;
    }

    public List<Empresa> darEmpresas() {
        EmpresaJpaController controladorEmpresa = new EmpresaJpaController(this.emf);
        List<Empresa> result = controladorEmpresa.findEmpresaEntities();
        return result;
    }

    public int darNumAlbum() {
        AlbumJpaController controladorAlbum = new AlbumJpaController(this.emf);
        int result = controladorAlbum.getAlbumCount();
        return result;
    }

    public Album agregarAlbum(String tipo, String nombre, LocalDate fchL, Empresa emp) {
        Album result = new Album();
        //LocalDate fechaLanzamiento = fchL;
        Date date = Date.from(fchL.atStartOfDay(ZoneId.systemDefault()).toInstant());
        long id = darNumAlbum() + 1001;

        result.setEmpresaNombreEmpresa(emp);
        result.setIdAlbum(id);
        result.setTituloAlbum(nombre);
        result.setTipo(tipo);
        result.setFechaLanzamiento(date);

        this.em.getTransaction().begin();
        this.em.persist(result);
        this.em.getTransaction().commit();

        return result;
    }

    public List<Generoxidioma> darGenerosEnEspañol() {
        GeneroxidiomaJpaController controladorGI = new GeneroxidiomaJpaController(this.emf);
        List<Generoxidioma> resp = controladorGI.findGeneroxidiomaEntities();
        List<Generoxidioma> result = new ArrayList<Generoxidioma>();

        for (Generoxidioma generoxidioma : resp) {
            if (generoxidioma.getGeneroxidiomaPK().getIdiomaNombreIdioma().equals("Español")) {
                result.add(generoxidioma);
            }
        }

        return result;
    }

    public boolean agregarCanciones(List<Cxi> secundarios, List<Cancion> canciones) {
        boolean can = false;
        this.em.getTransaction().begin();
        if (!can) {

            for (Cancion cancione : canciones) {
                em.persist(cancione);
            }

            for (Cxi sec : secundarios) {
                em.persist(sec);
            }

            can = true;
        }
        this.em.getTransaction().commit();
        return can;
    }

    public static Date getDate(int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public boolean EliminarCancionPlayList(CancionAux cancioncita) {
        StringBuilder sql = new StringBuilder("select * "
                + "from cancion "
                + "where titulo_c = '" + cancioncita.getTitulo_c() + "'");
        
        Query query = this.em.createNativeQuery(sql.toString(), Cancion.class);
        Cancion resultado = (Cancion) query.getSingleResult();
        return false;
             
    }
}
