package fr.iutvalence.projet.battleArenaGame.view;

import fr.iutvalence.projet.battleArenaGame.spell.Effect;

public class UserViewWindow implements UserView {

	@Override
	public void display(DisplayMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void askChoiceMenu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void askSpellElement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayElementChoice() {
		this.chElement.removeAll();
		this.chElement.add("Choose an element");
		for (Effect element : Effect.values()) {
			this.chElement.add(element.getElementName());
		}
		
	}

	@Override
	public void displayShapeChoice() {
		this.chShape.removeAll();
		this.chShape.add("Choose a shape");
		this.chShape.add("Fist");
		this.chShape.add("Ball");
		this.chShape.add("Sword");
		this.chShape.add("Square");
		this.chShape.add("Cross");
		this.chShape.add("Beam");
		this.chShape.add("Custom");
		
	}

}
