package jade.sma.hotel;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Scanner;

public class ClientAgent extends Agent {

    protected void setup() {
        // Ajouter un comportement pour envoyer une demande de réservation
        addBehaviour(new ReservationRequestBehaviour());
    }

    private class ReservationRequestBehaviour extends OneShotBehaviour {
        public void action() {
        	Scanner input = new Scanner(System.in); 
        	System.out.println(" Entrez le nombre de chambres souhaitées :  "); 
        	int RoomsRequest = input.nextInt();
            // Envoyer une demande de réservation à l'agent gestion des réservations
            ACLMessage msg = new ACLMessage(ACLMessage.CFP);
            msg.addReceiver(new AID("ReservationAgent", AID.ISLOCALNAME));
            msg.setContent(String.valueOf(RoomsRequest)); // Nombre de chambres demandées
            send(msg);
            
         // Attendre la réponse de l'agent gestion des réservations
            ACLMessage reply = blockingReceive();
            if (reply.getPerformative() == ACLMessage.AGREE) {
                System.out.println("La réservation des chambres a été validée. OK !");
            } else {
                System.out.println("La réservation des chambres a été refusée. ( Sature) .");
            }
        }
    }
}
