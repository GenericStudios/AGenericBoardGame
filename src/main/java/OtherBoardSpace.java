public class OtherBoardSpace extends BoardSpace {

    Enums.OtherBoardSpaceTypes actionType;

    public OtherBoardSpace(int myID, Enums.OtherBoardSpaceTypes myActionType) {
        super(myID);
        this.type = Enums.BoardSpaceTypes.other;
        this.actionType = myActionType;
    }

}
