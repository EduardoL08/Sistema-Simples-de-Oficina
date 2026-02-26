package Model;

public class MVeiculos {

    private int Id;
    private int IdCliente;
    private String Placas;
    private String Marcas;
    private String Modelos;
    private int Anos;
    private String Cores;
    private String Tipos;
    private double Kms;
    private String Obser;


    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int idCliente) {
        this.IdCliente = idCliente;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPlacas() {
        return Placas;
    }

    public void setPlacas(String placas) {
        this.Placas = placas;
    }

    public String getMarcas() {
        return Marcas;
    }

    public void setMarcas(String marcas) {
        this.Marcas = marcas;
    }

    public String getModelos() {
        return Modelos;
    }

    public void setModelos(String modelos) {
        this.Modelos = modelos;
    }

    public int getAnos() {
        return Anos;
    }

    public void setAnos(int anos) {
        Anos = anos;
    }

    public String getCores() {
        return Cores;
    }

    public void setCores(String cores) {
        this.Cores = cores;
    }

    public String getTipos() {
        return Tipos;
    }

    public void setTipos(String tipos) {
        this.Tipos = tipos;
    }

    public double getKms() {
        return Kms;
    }

    public void setKms(double kms) {
        this.Kms = kms;
    }

    public String getObser() {
        return Obser;
    }

    public void setObser(String obser) {
        this.Obser = obser;
    }
}
