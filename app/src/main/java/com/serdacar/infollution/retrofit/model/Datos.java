package com.serdacar.infollution.retrofit.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Root(name="Datos")
public class Datos {

    @ElementList(name="Dato_Horario", required=false, entry="Dato_Horario", inline=true)
    List<DatoHorario> datoHorario;

    public List<DatoHorario> getDatoHorario() {return this.datoHorario;}
    public void setDatoHorario(List<DatoHorario> value) {this.datoHorario = value;}


}