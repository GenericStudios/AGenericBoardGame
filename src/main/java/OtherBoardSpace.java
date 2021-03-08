public class OtherBoardSpace extends BoardSpace {

    Enums.OtherBoardSpaceTypes actionType;

    public OtherBoardSpace(int myID, Enums.OtherBoardSpaceTypes myActionType) {
        super(myID, Enums.BoardSpaceTypes.other);
        this.actionType = myActionType;
    }

}
