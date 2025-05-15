package endpoints;

public enum PlayerEndpoint {
    CREATE_PLAYER("/player/create/{editor}"),
    GET_PLAYER("/player/get"),
    DELETE_PLAYER("/player/delete/{editor}"),
    GET_ALL_PLAYERS("/player/get/all"),
    UPDATE_PLAYER("/player/update/{editor}/{id}");

    private final String path;

    PlayerEndpoint(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
