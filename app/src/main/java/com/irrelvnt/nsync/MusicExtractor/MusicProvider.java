package com.irrelvnt.nsync.MusicExtractor;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.irrelvnt.nsync.Player;
import com.irrelvnt.nsync.ui.discover.DiscoverFragment;
import com.irrelvnt.nsync.ui.home.HomeFragment;
import com.irrelvnt.nsync.ui.song.Song;

import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.ListExtractor;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.StreamExtractor;

import java.util.List;

public final class MusicProvider {
    private static StreamingService youtube;
    private static LinkHandlerFactory linkHandlerFactory;
    private static LinkHandler linkHandler;
    private static StreamExtractor streamExtractor;

    private static RecyclerView recyclerView;

    private static void initializeExtractor() throws Exception {
        youtube = NewPipe.getService(0);
        NewPipe.init(DownloaderImplementation.init(null));
        linkHandlerFactory = youtube.getStreamLHFactory();
    }

    private static String getAudio(String url) throws Exception {
        linkHandler = linkHandlerFactory.fromUrl(url);
        streamExtractor = youtube.getStreamExtractor(linkHandler);
        streamExtractor.fetchPage();
        List<AudioStream> audioStreams = streamExtractor.getAudioStreams();
        return audioStreams.get(0).url;
    }

    public static void getSongTask(Song song) {
        try {
            initializeExtractor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        new Thread(
                () -> {
                    try {
                        String audioURL = getAudio(song.getUrl());
                        mainThreadHandler.post(() -> {
                            try {
                                Player.sePlaybackSong(song,audioURL);
                            } catch (Exception e) {
                            }
                        });
                    } catch (Exception e) {
                    }
                }
        ).start();
    }

    public static void getInfoFromName(String title) {
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        Thread thread = new Thread(() -> {
            try {
                initializeExtractor();
                SearchQueryHandlerFactory searchQueryHandlerFactory = youtube.getSearchQHFactory();
                SearchQueryHandler query = searchQueryHandlerFactory.fromQuery(title);
                SearchExtractor searchExtractor = youtube.getSearchExtractor(query);
                searchExtractor.onFetchPage(NewPipe.getDownloader());
                ListExtractor.InfoItemsPage<InfoItem> page = searchExtractor.getInitialPage();
                Player.fetchedVideos.clear();
                Player.setFetchedVideos(page.getItems());
                mainThreadHandler.post(() -> {
                            recyclerView.getAdapter().notifyDataSetChanged();
                            DiscoverFragment.getInstance().changeVisibility();
                        }
                );
            } catch (Exception e) {
            }
        });
        thread.start();

    }

    public static void setRecyclerView(RecyclerView recyclerView1) {
        recyclerView = recyclerView1;
    }
}