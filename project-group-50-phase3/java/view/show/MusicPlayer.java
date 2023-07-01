package view.show;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

public class MusicPlayer {
    public static HashMap<String, MediaPlayer> musics = new HashMap<>();

    public static void playMusic(String url) {
        Media media = new Media(url);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        if (MusicPlayer.musics.containsKey(url)) {
            MusicPlayer.musics.replace(url, mediaPlayer);
        } else {
            MusicPlayer.musics.put(url, mediaPlayer);
        }
        clear();
    }

    public static void stopMusic(String url) {
        if (MusicPlayer.musics.isEmpty()) {
            return;
        }

        for (Map.Entry<String, MediaPlayer> set : MusicPlayer.musics.entrySet()) {
            if (set.getKey().equals(url)) {
                set.getValue().stop();
            }
        }
        clear();
    }

    private static void clear() {
        if (MusicPlayer.musics.isEmpty()) {
            return;
        }
        for (Map.Entry<String, MediaPlayer> set : MusicPlayer.musics.entrySet()) {
            if (set.getValue().getStatus().equals(MediaPlayer.Status.STOPPED)) {
                MusicPlayer.musics.remove(set);
            }
        }

    }

}
