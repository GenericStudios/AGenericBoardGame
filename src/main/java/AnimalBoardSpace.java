public class AnimalBoardSpace extends BoardSpace {
    public AnimalBoardSpace(int myID) {
        super(myID);
        this.type = Enums.BoardSpaceTypes.animal;
    }
}
