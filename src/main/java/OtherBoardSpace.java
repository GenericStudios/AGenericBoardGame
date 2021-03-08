public class OtherBoardSpace extends BoardSpace {

    private Enums.OtherBoardSpaceTypes actionType;

    public OtherBoardSpace(int myID, Enums.OtherBoardSpaceTypes myActionType) {
        super(myID, Enums.BoardSpaceTypes.other);
        this.actionType = myActionType;
    }

    public Enums.OtherBoardSpaceTypes getActionType() {
        return this.actionType;
    }

}
