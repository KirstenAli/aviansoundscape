package org.example.training.preprocessing;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.TarsosDSPAudioInputStream;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import be.tarsos.dsp.mfcc.MFCC;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class MFCCExtractor {

    public static float[] extractMFCC(String filePath){

        var mfccResults = new float[0];

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));

            TarsosDSPAudioFormat audioFormat = new TarsosDSPAudioFormat(audioInputStream.getFormat().getSampleRate(),
                    audioInputStream.getFormat().getSampleSizeInBits(),
                    audioInputStream.getFormat().getChannels(),
                    true,
                    false);
            TarsosDSPAudioInputStream audioStream = new JVMAudioInputStream(audioInputStream);
            AudioDispatcher dispatcher = new AudioDispatcher(audioStream, 1024, 0);

            // Compute MFCC features
            MFCC mfcc = new MFCC(1024, (int) audioFormat.getSampleRate());
            dispatcher.addAudioProcessor(mfcc);

            // Process the audio file
            dispatcher.run();

            mfccResults = mfcc.getMFCC();
        }

        catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        return mfccResults;
    }
}
