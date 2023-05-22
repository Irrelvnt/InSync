package com.irrelvnt.nsync.MusicExtractor;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import com.irrelvnt.nsync.Player;
import com.irrelvnt.nsync.ui.discover.DiscoverFragment;
import com.irrelvnt.nsync.ui.home.HomeFragment;

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

public class MusicProvider {
    static StreamingService youtube;
    static LinkHandlerFactory linkHandlerFactory;
    static LinkHandler linkHandler;
    static StreamExtractor streamExtractor;

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

    public static void getSongTask(String url) {
        try {
            initializeExtractor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        new Thread(
                () -> {
                    try {
                        String audioURL = getAudio(url);
                        Player.setUrl(audioURL);
                        HomeFragment.getInstance().changeVisibility();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
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
                Player.setFetchedVideos(page.getItems());
                mainThreadHandler.post(
                        () -> DiscoverFragment.getInstance().changeVisibility()
                );
            } catch (Exception e) {
                Log.e("TAG", "err", e);
            }
        });
        thread.start();

    }
}