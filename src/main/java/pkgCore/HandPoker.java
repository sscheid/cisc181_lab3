package pkgCore;

import java.util.ArrayList;
import java.util.Collections;
import pkgConstants.*;
import pkgEnum.eCardNo;
import pkgEnum.eHandStrength;
import pkgEnum.eRank;
import pkgEnum.eSuit;

public class HandPoker extends Hand {

	private ArrayList<CardRankCount> CRC = null;

	public HandPoker() {
		this.setHS(new HandScorePoker());
	}

	protected ArrayList<CardRankCount> getCRC() {
		return CRC;
	}

	@Override
	public HandScore ScoreHand() {

		Collections.sort(super.getCards());
		Frequency();
		
		HandScorePoker hs = new HandScorePoker();
		

		if (isRoyalFlush()) {
			return null;

		} else if (isStraightFlush()) {
			return null;

		} else if (isFourOfAKind()) {
			return null;
			
		} else if (isFullHouse()) {
			return null;
			
		} else if (isFlush()) {
			return null;
			
		} else if (isStraight()) {
			return null;
			
		} else if (isThreeOfAKind()) {
			return null;
			
		} else if (isTwoPair()) {
			return null;
			
		} else if (isPair()) {
			return null;
			
		} else if (isHighCard()) {
			return null;
			
		}
		else { 
		return null;
		}
	}

	public boolean isRoyalFlush() {
		boolean bIsRoyalFlush = false;
		boolean isStraightandRoyal = false;
		if (this.getCRC().size() == 5) {
			if ((this.getCRC().get(0).geteRank().getiRankNbr()-4 
					== this.getCRC().get(4).geteRank().getiRankNbr()) &&
					(this.getCRC().get(0).geteRank().getiRankNbr()==14)) {
				isStraightandRoyal = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.StraightFlush);
				int iGetCard = this.getCRC().get(0).getiCardPosition();
				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);
				HSP.setKickers(null);
				this.setHS(HSP);
			}
		}
		
		// now determine if the suits are the same
		boolean isFlush = false;

		int iCardCnt = super.getCards().size();
		int iSuitCnt = 0;

		for (eSuit eSuit : eSuit.values()) {
			for (Card c : super.getCards()) {
				if (eSuit == c.geteSuit()) {
					iSuitCnt++;
				}
			}
			if (iSuitCnt > 0)
				break;
		}

		if (iSuitCnt == iCardCnt) 
			isFlush = true;
		else
			isFlush = false;
		
		if (isStraightandRoyal == true && isFlush == true) {
			bIsRoyalFlush = true;
			HandScorePoker HSP = (HandScorePoker) this.getHS();
			HSP.seteHandStrength(eHandStrength.RoyalFlush);
			HSP.setHiCard(null); 
			HSP.setLoCard(null);
			HSP.setKickers(null);
			this.setHS(HSP);
		}
		return bIsRoyalFlush;
	}

	public boolean isStraightFlush() {
		boolean bisStraightFlush = false;
		// first determine if it is a straight
		boolean isStraight = false;
		if (this.getCRC().size() == 5) {
			if ((this.getCRC().get(0).geteRank().getiRankNbr()-4 
					== this.getCRC().get(4).geteRank().getiRankNbr()) &&
					(this.getCRC().get(0).geteRank().getiRankNbr()!=14)) {
				isStraight = true;
			}
		}
		
		// now determine if the suits are the same
		boolean isFlush = false;

		int iCardCnt = super.getCards().size();
		int iSuitCnt = 0;

		for (eSuit eSuit : eSuit.values()) {
			for (Card c : super.getCards()) {
				if (eSuit == c.geteSuit()) {
					iSuitCnt++;
				}
			}
			if (iSuitCnt > 0)
				break;
		}

		if (iSuitCnt == iCardCnt) 
			isFlush = true;
		else
			isFlush = false;
		
		if (isStraight == true && isFlush == true) {
			bisStraightFlush = true;
			HandScorePoker HSP = (HandScorePoker) this.getHS();
			HSP.seteHandStrength(eHandStrength.StraightFlush);
			int iGetCard = this.getCRC().get(0).getiCardPosition();
			HSP.setHiCard(this.getCards().get(iGetCard));
			HSP.setLoCard(null);
			HSP.setKickers(null);
			this.setHS(HSP);
		}
		
		return bisStraightFlush;
	}
	
	public boolean isFourOfAKind() {
		boolean bisFourOfAKind = false;
		if (this.getCRC().size() == 2) {
			if (this.getCRC().get(0).getiCnt() == Constants.FOUR_OF_A_KIND) {
				bisFourOfAKind = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.FourOfAKind);
				int iGetCard = this.getCRC().get(0).getiCardPosition();
				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);
				HSP.setKickers(FindTheKickers(this.getCRC()));
				this.setHS(HSP);
			}
		}
		return bisFourOfAKind;
	}

	public boolean isFullHouse() {
		boolean bisFullHouse = false;
		if (this.getCRC().size() == 2) {
			if ((this.getCRC().get(0).getiCnt() == Constants.THREE_OF_A_KIND) && (this.getCRC().get(1).getiCnt() == Constants.TWO_OF_A_KIND)){
				bisFullHouse = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.FullHouse);
				int iGetCard1 = this.getCRC().get(0).getiCardPosition();
				HSP.setHiCard(this.getCards().get(iGetCard1));
				int iGetCard2 = this.getCRC().get(1).getiCardPosition();
				HSP.setLoCard(this.getCards().get(iGetCard2));
				HSP.setKickers(null);
				this.setHS(HSP);
			}
		}
		return bisFullHouse;

	}

	public boolean isFlush() {
		boolean bisFlush = false;

		int iCardCnt = super.getCards().size();
		int iSuitCnt = 0;

		for (eSuit eSuit : eSuit.values()) {
			for (Card c : super.getCards()) {
				if (eSuit == c.geteSuit()) {
					iSuitCnt++;
				}
			}
			if (iSuitCnt > 0)
				break;
		}

		if (iSuitCnt == iCardCnt)
			bisFlush = true;
		else
			bisFlush = false;

		return bisFlush;
	}

	
	public boolean isStraight() {
		boolean bisStraight = false; // means it is straight but not a straight flush
		boolean isStraight = false; // just determines if straight
		if (this.getCRC().size() == 5) {
			if (this.getCRC().get(0).geteRank().getiRankNbr()-4 
					== this.getCRC().get(4).geteRank().getiRankNbr()) {
				isStraight = true;
			}
		}
		
		// ensure that it is not a straight flush
		boolean isFlush = false;

		int iCardCnt = super.getCards().size();
		int iSuitCnt = 0;

		for (eSuit eSuit : eSuit.values()) {
			for (Card c : super.getCards()) {
				if (eSuit == c.geteSuit()) {
					iSuitCnt++;
				}
			}
			if (iSuitCnt > 0)
				break;
		}

		if (iSuitCnt == iCardCnt)
			isFlush = true;
		else
			isFlush = false;
		
		if (isStraight == true && isFlush == false) {
			bisStraight = true;
			HandScorePoker HSP = (HandScorePoker) this.getHS();
			HSP.seteHandStrength(eHandStrength.Straight);
			int iGetCard = this.getCRC().get(0).getiCardPosition();
			HSP.setHiCard(this.getCards().get(iGetCard));
			HSP.setLoCard(null);
			HSP.setKickers(null);
			this.setHS(HSP);
		}

		return bisStraight;
	}

	// This is how to implement one of the 'counting' hand types. Testing to see if
	// there are three of the same rank.
	public boolean isThreeOfAKind() {
		boolean bisThreeOfAKind = false;
		if (this.getCRC().size() == 3) {
			if (this.getCRC().get(0).getiCnt() == Constants.THREE_OF_A_KIND) {
				bisThreeOfAKind = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.ThreeOfAKind);
				int iGetCard = this.getCRC().get(0).getiCardPosition();
				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);
				HSP.setKickers(FindTheKickers(this.getCRC()));
				this.setHS(HSP);
			}
		}
		return bisThreeOfAKind;
	}

	public boolean isTwoPair() {
		boolean bisTwoPair = false;
		if (this.getCRC().size() == 3) {
			if ((this.getCRC().get(0).getiCnt() == Constants.TWO_OF_A_KIND) && (this.getCRC().get(1).getiCnt() == Constants.TWO_OF_A_KIND)){
				bisTwoPair = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.TwoPair);
				int iGetCard = this.getCRC().get(0).getiCardPosition();
				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);
				HSP.setKickers(FindTheKickers(this.getCRC()));
				this.setHS(HSP);
			}
		}
		return bisTwoPair;
	}

	public boolean isPair() {
		boolean bisPair = false;
		if (this.getCRC().size() == 4) {
			if (this.getCRC().get(0).getiCnt() == Constants.TWO_OF_A_KIND) {
				bisPair = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.Pair);
				int iGetCard1 = this.getCRC().get(0).getiCardPosition();
				HSP.setHiCard(this.getCards().get(iGetCard1));
				int iGetCard2 = this.getCRC().get(1).getiCardPosition();
				// second pair (at position 1) is set to the low card
				HSP.setLoCard(this.getCards().get(iGetCard2));
				HSP.setKickers(FindTheKickers(this.getCRC()));
				this.setHS(HSP);
			}
		}
		return bisPair;
	}

	public boolean isHighCard() {
		boolean bisHighCard = false;
		if (this.getCRC().size() == 5) {
			if (this.getCRC().get(0).getiCnt() == Constants.ONE_OF_A_KIND) {
				bisHighCard = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.HighCard);
				int iGetCard = this.getCRC().get(0).getiCardPosition();
				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);
				HSP.setKickers(FindTheKickers(this.getCRC()));
				this.setHS(HSP);
			}
		}
		return bisHighCard; 
	}

	private ArrayList<Card> FindTheKickers(ArrayList<CardRankCount> CRC) {
		ArrayList<Card> kickers = new ArrayList<Card>();

		for (CardRankCount crcCheck : CRC) {
			if (crcCheck.getiCnt() == 1) {
				kickers.add(this.getCards().get(crcCheck.getiCardPosition()));
			}
		}

		return kickers;
	}

	private void Frequency() {
		CRC = new ArrayList<CardRankCount>();
		int iCnt = 0;
		int iPos = 0;
		for (eRank eRank : eRank.values()) {
			iCnt = (CountRank(eRank));
			if (iCnt > 0) {
				iPos = FindCardRank(eRank);
				CRC.add(new CardRankCount(eRank, iCnt, iPos));
			}
		}
		Collections.sort(CRC);
	}

	private int CountRank(eRank eRank) {
		int iCnt = 0;
		for (Card c : super.getCards()) {
			if (c.geteRank() == eRank) {
				iCnt++;
			}
		}
		return iCnt;
	}

	private int FindCardRank(eRank eRank) {
		int iPos = 0;

		for (iPos = 0; iPos < super.getCards().size(); iPos++) {
			if (super.getCards().get(iPos).geteRank() == eRank) {
				break;
			}
		}
		return iPos;
	}

}
