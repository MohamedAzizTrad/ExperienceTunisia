package esprit.tests;


import esprit.entites.Offre;
import esprit.services.OffreService;
import esprit.services.TypeService;

import java.time.LocalDate;

public class MainClass {
    public static void main(String[] args)  {

        OffreService ocd = new OffreService();
        TypeService ts = new TypeService();

        LocalDate dateDebut = LocalDate.of(2023,12,12);
        LocalDate dateFin = LocalDate.of(2023,12,12);


        Offre o = new Offre("localDate","test test","into local date",dateDebut, dateFin);

        System.out.println(o);

      //  ocd.ajouterOffre(o);


        System.out.println(ts.readById(1));

    }
}
