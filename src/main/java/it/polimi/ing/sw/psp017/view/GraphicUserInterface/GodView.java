package it.polimi.ing.sw.psp017.view.GraphicUserInterface;
import it.polimi.ing.sw.psp017.view.GodName;
import javax.swing.*;

public abstract class GodView {

    public static GodView getCard(GodName godName){

        switch(godName){
            case APOLLO:
                return new Apollo();
            case ARTEMIS:
                return new Artemis();
            case ATHENA:
                return new Athena();
            case ATLAS:
                return new Atlas();
            case DEMETER:
                return new Demeter();
            case HEPHAESTUS:
                return new Hephaestus();
            case MINOTAUR:
                return new Minotaur();
            case PAN:
                return new Pan();
            case PROMETHEUS:
                return new Prometheus();
            default:
                System.out.println("not existing card");
                return null;
        }
    }
    public abstract Icon getIcon();
    public abstract String getPowerDescription();
    public abstract Icon getIconDescription();
    public abstract Icon getIconPower();

   /* public static Icon getIcon(GodName godName){

       return new ImageIcon(GodView.class.getClassLoader().getResource(godName.toString() + "/" + godName.toString() + ".png"));
    }*/

         static class Apollo extends GodView{
            public Icon getIcon(){
                return new ImageIcon(GodView.class.getClassLoader().getResource("APOLLO/APOLLO.png"));
            }

             @Override
             public String getPowerDescription() {
                 return "You can switch your position with a enemy. Do you want turn off your power?";
             }

             @Override
             public Icon getIconDescription() {
                 return new ImageIcon(GodView.class.getClassLoader().getResource("APOLLO/APOLLO-1.png"));
             }

             @Override
             public Icon getIconPower() {
                 return new ImageIcon(GodView.class.getClassLoader().getResource("PowerButton/gp_skipswap.png"));

             }
         }

    static class Artemis extends GodView{
        public Icon getIcon(){
            return new ImageIcon(GodView.class.getClassLoader().getResource("ARTEMIS/podium-characters-Artemis.png"));
        }

        @Override
        public String getPowerDescription() {
            return "You can switch your position with a enemy. Do you want turn off your power?";
        }

        @Override
        public Icon getIconDescription() {
            return new ImageIcon(GodView.class.getClassLoader().getResource("ARTEMIS/ARTEMIS-1.png"));
        }

        @Override
        public Icon getIconPower() {
            return new ImageIcon(GodView.class.getClassLoader().getResource("PowerButton/gp_skipmove.png"));
        }
    }
    static class Athena extends GodView{
        public Icon getIcon(){
            return new ImageIcon(GodView.class.getClassLoader().getResource("ATHENA/podium-characters-Athena.png"));
        }

        @Override
        public String getPowerDescription() {
            return "You can switch your position with a enemy. Do you want turn off your power?";
        }

        @Override
        public Icon getIconDescription() {
            return new ImageIcon(GodView.class.getClassLoader().getResource("ATHENA/ATHENA-1.png"));
        }

        @Override
        public Icon getIconPower() {
            return null;
        }
    }
    static class Atlas extends GodView{
        public Icon getIcon(){
            return new ImageIcon(GodView.class.getClassLoader().getResource("ATLAS/podium-characters-Atlas.png"));
        }

        @Override
        public String getPowerDescription() {
            return "You can switch your position with a enemy. Do you want turn off your power?";
        }

        @Override
        public Icon getIconDescription() {
            return new ImageIcon(GodView.class.getClassLoader().getResource("ATLAS/ATLAS-1.png"));
        }

        @Override
        public Icon getIconPower() {
            return new ImageIcon(GodView.class.getClassLoader().getResource("PowerButton/dome_complete.png"));
        }
    }
    static class Demeter extends GodView{
        public Icon getIcon(){
            return new ImageIcon(GodView.class.getClassLoader().getResource("DEMETER/podium-characters-Demeter.png"));
        }

        @Override
        public String getPowerDescription() {
            return "You can switch your position with a enemy. Do you want turn off your power?";
        }

        @Override
        public Icon getIconDescription() {
            return new ImageIcon(GodView.class.getClassLoader().getResource("DEMETER/DEMETER-1.png"));
        }

        @Override
        public Icon getIconPower() {
            return new ImageIcon(GodView.class.getClassLoader().getResource("PowerButton/gp_skipbuild.png"));
        }
    }
    static class Hephaestus extends GodView{
        public Icon getIcon(){
            return new ImageIcon(GodView.class.getClassLoader().getResource("HEPHAESTUS/podium-characters-Hephaestus.png"));
        }

        @Override
        public String getPowerDescription() {
            return "You can switch your position with a enemy. Do you want turn off your power?";
        }

        @Override
        public Icon getIconDescription() {
            return new ImageIcon(Apollo.class.getClassLoader().getResource("HEPHAESTUS/HEPHAESTUS-1.png"));
        }

        @Override
        public Icon getIconPower() {
            return new ImageIcon(Apollo.class.getClassLoader().getResource("PowerButton/gp_skipbuild.png"));
        }
    }
    static class Minotaur extends GodView{
        public Icon getIcon(){
            return new ImageIcon(GodView.class.getClassLoader().getResource("MINOTAUR/podium-characters-Minotaur.png"));
        }

        @Override
        public String getPowerDescription() {
            return "You can switch your position with a enemy. Do you want turn off your power?";
        }

        @Override
        public Icon getIconDescription() {
            return new ImageIcon(Apollo.class.getClassLoader().getResource("MINOTAUR/_0000s_0009_god_and_hero_powers0049.png"));
        }

        @Override
        public Icon getIconPower() {
            return new ImageIcon(Apollo.class.getClassLoader().getResource("PowerButton/gp_skipmove.png"));
        }
    }
    static class Pan extends GodView{
        public Icon getIcon(){
            return new ImageIcon(GodView.class.getClassLoader().getResource("PAN/podium-characters-Pan.png"));
        }

        @Override
        public String getPowerDescription() {
            return "You can switch your position with a enemy. Do you want turn off your power?";
        }

        @Override
        public Icon getIconDescription() {
            return new ImageIcon(Apollo.class.getClassLoader().getResource("PAN/PAN-1.png"));
        }

        @Override
        public Icon getIconPower() {
            return null;
        }
    }
    static class Prometheus extends GodView{
        public Icon getIcon(){
            return new ImageIcon(GodView.class.getClassLoader().getResource("PROMETHEUS/podium-characters-Prometheus.png"));
        }

        @Override
        public String getPowerDescription() {
            return "You can switch your position with a enemy. Do you want turn off your power?";
        }

        @Override
        public Icon getIconDescription() {
            return new ImageIcon(Apollo.class.getClassLoader().getResource("PROMETHEUS/PROMETHEUS-1.png"));
        }

        @Override
        public Icon getIconPower() {
            return new ImageIcon(Apollo.class.getClassLoader().getResource("PowerButton/gp_buildbeforemove.png"));
        }
    }


}
