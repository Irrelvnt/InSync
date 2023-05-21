package com.irrelvnt.nsync.MusicExtractor;

import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.StreamExtractor;

import java.io.IOException;
import java.util.List;

public class YoutubeExtractor {
    static StreamingService youtube;
    static LinkHandlerFactory linkHandlerFactory;
    static LinkHandler linkHandler;
    static StreamExtractor streamExtractor;

    public static String getInstance(String url) throws ExtractionException, IOException {
        youtube = NewPipe.getService(0);
        NewPipe.init(DownloaderTestImpl.init(null));
        linkHandlerFactory = youtube.getStreamLHFactory();
        linkHandler = linkHandlerFactory.fromUrl(url);
        streamExtractor = youtube.getStreamExtractor(linkHandler);
        streamExtractor.fetchPage();
        List<AudioStream> audioStreams = streamExtractor.getAudioStreams();
        return audioStreams.get(0).url;

    }
}
