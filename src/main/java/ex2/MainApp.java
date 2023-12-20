package ex2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int opt=0;

        System.out.println("cate instrumente? (enter 0 to skip)");
        int n=sc.nextInt();
        Set<InstrumentMuzical> instrumentMuzicalSet = new LinkedHashSet<InstrumentMuzical>();
        for(int i=0;i<n;i++) {
            adaugareInstrument(instrumentMuzicalSet);
        }

        do{
            System.out.println("0=iesire");
            System.out.println("1=afisare");
            System.out.println("2=adaugare");
            System.out.println("3=salvare JSON");
            System.out.println("4=incarcare JSON");
            System.out.println("5=stergere pret>3000");
            System.out.println("6=afisare Chitare");
            System.out.println("7=afisare Tobe");
            System.out.println("8=chitara care are cele mai multe corzi");
            System.out.println("9=ordonare tobe acustice");

            opt=sc.nextInt();
            switch (opt){
                case 0->{
                    System.out.println("Iesire din program");
                }
                case 1->{
                    afisareSet(instrumentMuzicalSet);
                }
                case 2->{
                    adaugareInstrument(instrumentMuzicalSet);
                }
                case 3->{
                    scriere(instrumentMuzicalSet);
                }
                case 4->{
                    instrumentMuzicalSet=citire();
                }
                case 5->{
                    stergereInstrumente(instrumentMuzicalSet);
                }
                case 6->{
                    afisareChitare(instrumentMuzicalSet);
                }
                case 7->{
                    afisareTobe(instrumentMuzicalSet);
                }
                case 8->{
                    maxCorzi(instrumentMuzicalSet);
                }
                case 9->{
                    afisareOrdonataTobeAcustice(instrumentMuzicalSet);
                }

            }
        }while(opt!=0);

    }

    private static void afisareOrdonataTobeAcustice(Set<InstrumentMuzical> instrumentMuzicalSet) {
        Set<SetTobe> tobeAcustice=instrumentMuzicalSet
                .stream()
                .filter(a->a instanceof SetTobe)
                .map(a->(SetTobe)a)
                .sorted(Comparator.comparing(SetTobe::getNr_tobe))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        tobeAcustice.forEach(System.out::println);
    }

    private static void maxCorzi(Set<InstrumentMuzical> instrumentMuzicalSet) {
        Optional<Chitara> chitaraMaxCorzi = instrumentMuzicalSet.stream()
                .filter(instrument -> instrument instanceof Chitara)
                .map(instrument -> (Chitara) instrument) // Cast to Chitara
                .max(Comparator.comparingInt(Chitara::getNr_corzi));

        chitaraMaxCorzi.ifPresent(chitara -> System.out.println("Chitara with the most corzii: " + chitara));
    }


    private static void afisareTobe(Set<InstrumentMuzical> instrumentMuzicalSet) {
        Set<InstrumentMuzical> tobe = instrumentMuzicalSet
                .stream()
                .filter((a)->a.getClass()== SetTobe.class)
                .collect(Collectors.toSet());
        tobe.forEach(System.out::println);
    }

    private static void afisareChitare(Set<InstrumentMuzical> instrumentMuzicalSet) {
        Set<InstrumentMuzical> chitare = instrumentMuzicalSet
                .stream()
                .filter((a)->a instanceof Chitara)
                .collect(Collectors.toSet());
        chitare.forEach(System.out::println);

    }

    private static void stergereInstrumente(Set<InstrumentMuzical> instrumentMuzicalSet) {
        instrumentMuzicalSet.removeIf((a)->a.getPret()>3000);
    }

    public static void scriere(Set<InstrumentMuzical> lista) {
        try {
            ObjectMapper mapper=new ObjectMapper();
            mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
            File file=new File("src/main/resources/instrumente.json");
            mapper.writeValue(file,lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<InstrumentMuzical> citire() {
        try {
            File file=new File("src/main/resources/instrumente.json");
            ObjectMapper mapper=new ObjectMapper();

            Set<InstrumentMuzical> instrumente = mapper
                    .readValue(file, new TypeReference<Set<InstrumentMuzical>>(){});
            return instrumente;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void afisareSet(Set<InstrumentMuzical> instrumentMuzicalSet) {
        for(InstrumentMuzical i:instrumentMuzicalSet){
            System.out.println(i);
        }
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("instrumente: " + mapper.getTypeFactory().constructCollectionType(Set.class, InstrumentMuzical.class));
    }

    private static void adaugareInstrument(Set<InstrumentMuzical> instrumentMuzicalSet) {
        Scanner sc=new Scanner(System.in);
        System.out.println("instrument=");
        String linie = sc.nextLine();
        StringTokenizer st = new StringTokenizer(linie);
        String prod = st.nextToken().toString();
        Float pret = Float.parseFloat(st.nextToken().toString());
        Tip tip = Tip.valueOf(st.nextToken().toString());
        switch (tip) {
            case CHITARA -> {
                TipChitara tipc = TipChitara.valueOf(st.nextToken().toString());
                int nrc = Integer.parseInt(st.nextToken().toString());
                Chitara c= new Chitara(prod,pret,tip,tipc,nrc);
                instrumentMuzicalSet.add(c);
            }
            case SET_TOBE -> {
                TipTobe tipt = TipTobe.valueOf(st.nextToken().toString());
                int nrt = Integer.parseInt(st.nextToken().toString());
                int nrcin = Integer.parseInt(st.nextToken().toString());
                SetTobe stb=new SetTobe(prod,pret,tip,tipt,nrt,nrcin);
                instrumentMuzicalSet.add(stb);
            }
        }
    }
}
