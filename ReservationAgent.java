package jade.sma.hotel;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ReservationAgent extends Agent {

    protected void setup() {
        // Ajouter un comportement pour coordonner les réservations
        addBehaviour(new ReservationCoordinationBehaviour());
    }

    private class ReservationCoordinationBehaviour extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                // Traiter la demande de réservation du client
                int requestedRooms = Integer.parseInt(msg.getContent());
                ACLMessage reply = msg.createReply();

                // Envoyer la demande à un agent hôtel (ici, un agent hôtel fictif)
                ACLMessage hotelRequest = new ACLMessage(ACLMessage.CFP);
                hotelRequest.addReceiver(new AID("HotelAgent", AID.ISLOCALNAME));
                hotelRequest.setContent(msg.getContent());
                send(hotelRequest);

                // Attendre la réponse de l'agent hôtel
                ACLMessage hotelReply = blockingReceive();
                if (hotelReply.getPerformative() == ACLMessage.AGREE) {
                    reply.setPerformative(ACLMessage.AGREE);
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



