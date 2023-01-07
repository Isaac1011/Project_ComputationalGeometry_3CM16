/*
************************************************************************************
                            INSTITUTO POLITÉCNICO NACIONAL 
                             ESCUELA SUPERIOR DE CÓMPUTO


                               Computational Geometry
                                      Proyecto


Alumno: Sandoval Acevedo Isaac

Grupo: 3CM16

Profesora: Rosaura Palma Orozco

Título del proyecto: Programa parar distribuir una cantidad n de 
puntos en un espacio finito euclidiano de 2 dimensiones 


                                  19 de enero de 2023


************************************************************************************
*/



package com.mycompany.proyectogeometriacomputacional;

import java.util.Random;

import java.util.ArrayList;
import java.util.Scanner;

public class Plano {

    private ArrayList<Punto> puntos;//Arreglo para los puntos
    private int fila;
    private int columna;
    private String planoCartesiano[][];//Matriz del plano cartesiano

    
    public Plano(String plano[][], int fila, int columna) {//Constructor   
        puntos = new ArrayList<Punto>();
        this.fila = fila;
        this.columna = columna;
        this.planoCartesiano = plano;
    }//Plano

    
    public void agregarPunto(int x, int y) {
        this.puntos.add(new Punto(x, y));
        this.planoCartesiano[y][x] = "*";//Agregamos graficamente el punto
        //Recordemos que las coordenadas las ponemos al reves 
    }//agregarPunto

    
    public void agregarPunto() {
        Random aleatorio = new Random();
        int x = aleatorio.nextInt(8) + 1;//Me da un número entre 1 y 9 
        int y = aleatorio.nextInt(8) + 1;//Me da un número entre 1 y 9 

        //puntos.add(new Punto(x, y));
        if (verificarCasillaOcupada(this.planoCartesiano, x, y) == 1) {//Si la casilla está ocupada
            agregarPunto();//Volveamos a llamar a la función
        } else {//Si la casilla no está ocupada
            this.puntos.add(new Punto(x, y));
            this.planoCartesiano[y][x] = "*";//Agregamos graficamente el punto
            //Recordemos que las coordenadas las ponemos al reves            
        }
    }//agregarPunto

    
    public int verificarCasillaOcupada(String matriz[][], int x, int y) {
        if (this.planoCartesiano[y][x] == "*") {//Coordenada ocupada
            return 1;
        } else {//Coordenada no ocupada
            return 0;
        }
    }//verificarCasillaOcupada

    
    public void iniciarMatriz() {
        for (int i = 0; i < this.fila; i++) {
            for (int j = 0; j < this.columna; j++) {
                String espacio = " ";
                this.planoCartesiano[i][j] = espacio;
            }
        }
        //Colocamos los indices
        for (int i = 0; i < this.fila; i++) {
            this.planoCartesiano[i][0] = i + "";
        }
        for (int j = 0; j < this.columna; j++) {
            this.planoCartesiano[0][j] = j + "";
        }

    }//iniciarMatriz

    
    public void imprimirMatriz() {
        for (int i = 0; i < this.fila; i++) {
            for (int j = 0; j < this.columna; j++) {
                System.out.print("[" + this.planoCartesiano[i][j] + "]");
            }
            System.out.println("");
        }
        System.out.println("\n\n\n");
    }//imprimirMatriz

    
    public double distanciaEuclidiana(Punto a, Punto b) {
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }//distanciaEuclidiana

    
    public int movimientoValido(Punto a) {
        if (a.x < this.columna && a.y < this.fila /*evito pasarme de la matriz*/
                && a.x > 0 && a.y > 0 /*hacer que las coordenadas sean positivas distintas de 0*/
                && verificarCasillaOcupada(this.planoCartesiano, a.x, a.y) == 0) {
            return 1; //movimiento valido
        } else { 
            return 0;//movimiento no valido
        }
    }//movimientoValido
    
    
    public void quitarPuntoGraficamente(Punto a){
        this.planoCartesiano[a.y][a.x] = " ";
    }//quitarPuntoGraficamente
    
    
    public void ponerPuntoGraficamente(Punto a){
        this.planoCartesiano[a.y][a.x] = "*";
    }//ponerPuntoGraficamente


    public double mejorarDistancia(Punto aux, Punto b, Punto a, double distancia, double distanciaOptima) {
        if (movimientoValido(aux) == 1) {
            distancia = distanciaEuclidiana(aux, b);
            if (distancia >= distanciaOptima) {//Mejoramos la distancia
                distanciaOptima = distancia;
                quitarPuntoGraficamente(a);
                a.cambiarCoordenadas(aux.x, aux.y);//Cambio de coordenadas
                ponerPuntoGraficamente(a);
                //return distanciaOptima;
                //Falta quitar y poner el nuevo punto de manera grafica
            }
            else{//No mejoramos la distancia
                
            }
            return distanciaOptima;
        }
        else{
            return distanciaOptima;
        }       
    }//mejorarDistancia

    
    public void moverPunto(Punto a, Punto b) {
        double distanciaOptima = distanciaEuclidiana(a, b);
        double distancia = 0;
        
        Punto aux1 = new Punto(a.x, a.y - 1);
        Punto aux2 = new Punto(a.x + 1, a.y - 1);
        Punto aux3 = new Punto(a.x + 1, a.y);
        Punto aux4 = new Punto(a.x + 1, a.y + 1);
        Punto aux5 = new Punto(a.x, a.y + 1);
        Punto aux6 = new Punto(a.x - 1, a.y + 1);
        Punto aux7 = new Punto(a.x - 1, a.y);
        Punto aux8 = new Punto(a.x - 1, a.y - 1);

        
        distanciaOptima = mejorarDistancia(aux1, b, a, distancia, distanciaOptima);
        distanciaOptima = mejorarDistancia(aux2, b, a, distancia, distanciaOptima);
        distanciaOptima = mejorarDistancia(aux3, b, a, distancia, distanciaOptima);
        distanciaOptima = mejorarDistancia(aux4, b, a, distancia, distanciaOptima);
        distanciaOptima = mejorarDistancia(aux5, b, a, distancia, distanciaOptima);
        distanciaOptima = mejorarDistancia(aux6, b, a, distancia, distanciaOptima);
        distanciaOptima = mejorarDistancia(aux7, b, a, distancia, distanciaOptima);
        distanciaOptima = mejorarDistancia(aux8, b, a, distancia, distanciaOptima);
    }//moverPunto
    
    
    public int factorial(int n){
        if(n == 0){
            return 1;
        }
        else{
            return n*factorial(n-1);
        }
    }
    
    
    public int calcularCombinaciones(int n, int k){
        int N = factorial(n);
        int K = factorial(k);
        return ((N/(K * factorial(n-k))));
    }
    
    
    public int calcularPermutaciones(int n, int k){
        int N = factorial(n);
        int K = factorial(n-k);
        return (N/K);
    }
    
    
    public void imprimirCoordenadas(){
        int i;
        for(i=0;i<puntos.size();i++){
            System.out.println("x = "+puntos.get(i).x + " y = "+puntos.get(i).y);
        }
    }
    

    //Creamos la clase Punto
    private class Punto {

        private int x, y;

        public Punto(int x, int y) {
            this.x = x;
            this.y = y;
        }//Constructor Punto

        public void cambiarCoordenadas(int x, int y) {
            this.x = x;
            this.y = y;
        }//cambiarCoordenadas

    }//clase Punto

    
    public static void main(String[] args) {
        int Filas = 10;
        int Columnas = 10;
        String plano[][] = new String[Filas][Columnas];

        Plano p = new Plano(plano, Filas, Columnas);
        p.iniciarMatriz();
        
        System.out.println("*****************************************************");
        System.out.println("Proyecto final");
        System.out.println("Alumno: Sandoval Acevedo Isaac");
        System.out.println("Unidad de aprendizaje: Computational Geometry");
        System.out.println("Grupo: 3CM16");
        System.out.println("Profesora: Rosaura Palma Orozco");
        System.out.println("Ciclo escolar: 2023_1");
        System.out.println("*****************************************************");
        
        System.out.println("\n \nDigite la cantidad de puntos que desea trabajar: ");
        Scanner entrada = new Scanner(System.in);
        int cantidadPuntos = entrada.nextInt();
        int i;
        for(i=0;i<cantidadPuntos;i++){
            p.agregarPunto();
        }
        

        System.out.println("Puntos iniciales");
        p.imprimirCoordenadas();
        
        p.imprimirMatriz();
           
        int iteraciones = 6;
        int k = 0;
        for(k=0;k<iteraciones;k++){    
            int i2 = 0;
            int j = 0;
            int contador = 1;
            for(i2 = 0;i2<p.puntos.size();i2++){
                for(j=1;j<p.puntos.size();j++){
                    if(j == i2){
                        int jAux = j-contador;
                        contador++;
                        p.moverPunto(p.puntos.get(jAux), p.puntos.get(i2));
                    }
                    else{
                        p.moverPunto(p.puntos.get(j), p.puntos.get(i2));
                    }                      
                }
            }
        }

        System.out.println("Puntos finales");
        p.imprimirCoordenadas();
        p.imprimirMatriz();
        
        System.out.println("*****************************************************");
        System.out.println("Proyecto final");
        System.out.println("Alumno: Sandoval Acevedo Isaac");
        System.out.println("Unidad de aprendizaje: Computational Geometry");
        System.out.println("Grupo: 3CM16");
        System.out.println("Profesora: Rosaura Palma Orozco");
        System.out.println("Ciclo escolar: 2023_1");        
        System.out.println("*****************************************************");
       
    }//main

}//clase Plano

