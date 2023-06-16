package org.example.training.preprocessing;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.TarsosDSPAudioInputStream;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import be.tarsos.dsp.mfcc.MFCC;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class MFCCExtractor{
    public static float[] extractMFCC(String filePath, int samplesPerFrame,
                                      int bufferOverlap, int amountOfCepstrumCoef,
                                      int amountOfMelFilters, float lowerFilterFreq){

        var mfccResults = new float[0];

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));

            TarsosDSPAudioInputStream audioStream = new JVMAudioInputStream(audioInputStream);

            AudioDispatcher dispatcher = new AudioDispatcher(audioStream, samplesPerFrame, bufferOverlap);

            var sampleRate = audioInputStream.getFormat().getSampleRate();

            // Compute MFCC features
            MFCC mfcc = new MyMFCC(samplesPerFrame, (int) sampleRate,
                    amountOfCepstrumCoef, amountOfMelFilters,
                    lowerFilterFreq, sampleRate / 2.0F);

            dispatcher.addAudioProcessor(mfcc);
            // Process the audio file
            dispatcher.run();
            mfccResults = mfcc.getMFCC();
        }

        catch (UnsupportedAudioFileException | IOException e){
            e.printStackTrace();
        }

        return mfccResults;
    }

    public static float[] extractMFCC(String filePath){
        return extractMFCC(filePath, 1024,
                0, 13,
                13, 133.3334F);
    }
}
