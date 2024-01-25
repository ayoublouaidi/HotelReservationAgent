package jade.sma.hotel;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class HotelAgent extends Agent {

    private String hotelName;
    private String hotelAddress;
    private int availableRooms;

    protected void setup() {
        // Initialiser les attributs de l'agent h�tel
        String hotelName = "AYOUB CLUB";
        String hotelAddress = "RN5, Ath Mansour 1001 , Mch'dallah , Bouira,Algerie";
        int availableRooms = 10;

        // Ajouter un comportement pour traiter les demandes de r�servation
        addBehaviour(new ReservationBehaviour());
    }

    private class ReservationBehaviour extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                // Traiter la demande de r�servation du client
                int requestedRooms = Integer.parseInt(msg.getContent());
                ACLMessage reply = msg.createReply();

                if (availableRooms >= requestedRooms) {
                    reply.setPerformative(ACLMessage.AGREE);
                    availableRooms -= requestedRooms;
                } else {
                    reply.setPerformative(ACLMessage.REFUSE);
                }

                send(reply);
            } else {
                block();
            }
        }
    }
}

