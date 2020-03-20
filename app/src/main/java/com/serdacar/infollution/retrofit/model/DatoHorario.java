package com.serdacar.infollution.retrofit.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="DatoHorario")
public class DatoHorario {

    @Element(name="punto_muestreo", required=false)
    String puntoMuestreo;

    @Element(name="H21", required=false)
    String h21;

    @Element(name="H20", required=false)
    String h20;

    @Element(name="H23", required=false)
    String h23;

    @Element(name="H22", required=false)
    String h22;

    @Element(name="H24", required=false)
    String h24;

    @Element(name="V21", required=false)
    String v21;

    @Element(name="V20", required=false)
    String v20;

    @Element(name="V23", required=false)
    String v23;

    @Element(name="V22", required=false)
    String v22;

    @Element(name="V24", required=false)
    String v24;

    @Element(name="municipio", required=false)
    String municipio;

    @Element(name="H01", required=false)
    String h01;

    @Element(name="H03", required=false)
    String h03;

    @Element(name="H02", required=false)
    String h02;

    @Element(name="provincia", required=false)
    String provincia;

    @Element(name="H05", required=false)
    String h05;

    @Element(name="H04", required=false)
    String h04;

    @Element(name="H07", required=false)
    String h07;

    @Element(name="H06", required=false)
    String h06;

    @Element(name="H09", required=false)
    String h09;

    @Element(name="H08", required=false)
    String h08;

    @Element(name="V01", required=false)
    String v01;

    @Element(name="V03", required=false)
    String v03;

    @Element(name="V02", required=false)
    String v02;

    @Element(name="estacion", required=false)
    String estacion;

    @Element(name="mes", required=false)
    String mes;

    @Element(name="V05", required=false)
    String v05;

    @Element(name="V04", required=false)
    String v04;

    @Element(name="V07", required=false)
    String v07;

    @Element(name="V06", required=false)
    String v06;

    @Element(name="V09", required=false)
    String v09;

    @Element(name="V08", required=false)
    String v08;

    @Element(name="ano", required=false)
    String ano;

    @Element(name="H10", required=false)
    String h10;

    @Element(name="H12", required=false)
    String h12;

    @Element(name="H11", required=false)
    String h11;

    @Element(name="magnitud", required=false)
    String magnitud;

    @Element(name="H14", required=false)
    String h14;

    @Element(name="H13", required=false)
    String h13;

    @Element(name="H16", required=false)
    String h16;

    @Element(name="H15", required=false)
    String h15;

    @Element(name="H18", required=false)
    String h18;

    @Element(name="H17", required=false)
    String h17;

    @Element(name="H19", required=false)
    String h19;

    @Element(name="V10", required=false)
    String v10;

    @Element(name="V12", required=false)
    String v12;

    @Element(name="V11", required=false)
    String v11;

    @Element(name="V14", required=false)
    String v14;

    @Element(name="V13", required=false)
    String v13;

    @Element(name="V16", required=false)
    String v16;

    @Element(name="V15", required=false)
    String v15;

    @Element(name="V18", required=false)
    String v18;

    @Element(name="dia", required=false)
    String dia;

    @Element(name="V17", required=false)
    String v17;

    @Element(name="V19", required=false)
    String v19;

    public String getPuntoMuestreo() {return this.puntoMuestreo;}

    public String getH21() {return this.h21;}

    public String getH20() {return this.h20;}

    public String getH23() {return this.h23;}

    public String getH22() {return this.h22;}

    public String getH24() {return this.h24;}

    public String getMunicipio() {return this.municipio;}

    public String getH01() {return this.h01;}

    public String getH03() {return this.h03;}

    public String getH02() {return this.h02;}

    public String getProvincia() {return this.provincia;}

    public String getH05() {return this.h05;}

    public String getH04() {return this.h04;}

    public String getH07() {return this.h07;}

    public String getH06() {return this.h06;}

    public String getH09() {return this.h09;}

    public String getH08() {return this.h08;}

    public String getEstacion() {return this.estacion;}

    public String getMes() {return this.mes;}

    public String getAno() {return this.ano;}

    public String getH10() {return this.h10;}

    public String getH12() {return this.h12;}

    public String getH11() {return this.h11;}

    public String getMagnitud() {return this.magnitud;}

    public String getH14() {return this.h14;}

    public String getH13() {return this.h13;}

    public String getH16() {return this.h16;}

    public String getH15() {return this.h15;}

    public String getH18() {return this.h18;}

    public String getH17() {return this.h17;}

    public String getH19() {return this.h19;}

    public String getDia() {return this.dia;}

}
