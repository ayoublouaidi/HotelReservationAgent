package jade.sma.hotel;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Scanner;

public class ClientAgent extends Agent {

    protected void setup() {
        // Ajouter un comportement pour envoyer une demande de r�servation
        addBehaviour(new ReservationRequestBehaviour());
    }

    private class ReservationRequestBehaviour extends OneShotBehaviour {
        public void action() {
        	Scanner input = new Scanner(System.in); 
        	System.out.println(" Entrez le nombre de chambres souhait�es :  "); 
        	int RoomsRequest = input.nextInt();
            // Envoyer une demande de r�servation � l'agent gestion des r�servations
            ACLMessage msg = new ACLMessage(ACLMessage.CFP);
            msg.addReceiver(new AID("ReservationAgent", AID.ISLOCALNAME));
            msg.setContent(String.valueOf(RoomsRequest)); // Nombre de chambres demand�es
            send(msg);
            
         // Attendre la r�ponse de l'agent gestion des r�servations
            ACLMessage reply = blockingReceive();
            if (reply.getPerformative() == ACLMessage.AGREE) {
                System.out.println("La r�servation des chambres a �t� valid�e. OK !");
            } else {
                System.out.println("La r�servation des chambres a �t� refus�e. ( Sature) .");
            }
        }
    }
}
