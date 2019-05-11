package com.appmoviles.muriel.retodeezermuriel.services;

import java.io.IOException;

public class ServiceManager {

    //Buscar playlist por parecidos
    public static final String DEEZER_URL_SEARCH_PLAYLIST = "https://api.deezer.com/search/playlist?q=";

    //Busca una playlist con id
    public static final String DEEZER_URL_SEARCH_PLAYLIST_ID = "https://api.deezer.com/playlist/";

    public static final String DEEZER_URL_SEARCH_SONG_ID = "https://api.deezer.com/track/";

    public static class SearchPlayListGET {
        OnResponseListener listener;

        public SearchPlayListGET(OnResponseListener listener, String busqueda) {
            this.listener = listener;
            HTTPSWebUtilDomi util = new HTTPSWebUtilDomi();
            try {
                String response = util.GETrequest(DEEZER_URL_SEARCH_PLAYLIST + busqueda);
                listener.onResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public interface OnResponseListener {
            void onResponse(String response);
        }
    }

    public static class PlayListByIdGET {
        OnResponseListener listener;

        public PlayListByIdGET(OnResponseListener listener, String id_playlist) {
            this.listener = listener;
            HTTPSWebUtilDomi util = new HTTPSWebUtilDomi();
            try {
                String response = util.GETrequest(DEEZER_URL_SEARCH_PLAYLIST_ID + id_playlist);
                listener.onResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public interface OnResponseListener {
            void onResponse(String response);
        }
    }

    public static class TrackByIdGET {
        OnResponseListener listener;

        public TrackByIdGET (OnResponseListener listener, String id_track) {
            this.listener = listener;
            HTTPSWebUtilDomi util = new HTTPSWebUtilDomi();
            try {
                String response = util.GETrequest(DEEZER_URL_SEARCH_SONG_ID + id_track);
                listener.onResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public interface OnResponseListener {
            void onResponse(String response);
        }
    }

}