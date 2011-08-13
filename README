CleanCLI library
================

_Please note that this help is not sufficient, it contains examples of usage
only. I will write complete guide ASAP._

Usage examples
--------------

// java Lame --vbr-new -V0 --noreplaygain -q 0 -m j awesometune.wav awesomefile.mp3
import org.aerialsounds.ccli.*
import java.util.Iterator;

public class Lame {
    static public void main(String[] args) {
        CCli repository = new CCli(args);

        Option vbrNew = repository.createOption(
            OptionTypes.LONG,
            "vbr-new",
            false,
            ValueTypes.BOOLEAN,
            "Detected new mode for VBR"
        );
        Option useReplayGain = repository.createOption(
            OptionTypes.CUSTOM,
            "--noreplaygain",
            false,
            ValueTypes.BOOLEAN,
            "Does ReplayGain should be used?"
        );
        Option vBitrate = repository.createOption(
            OptionTypes.SHORT,
            "V",
            2,
            ValueTypes.INTEGER,
            "Detects VBR quality algorithm"
        );
        Option stereoMode = repository.createOption(
            OptionTypes.SHORT,
            "m",
            'c',
            ValueTypes.CHAR,
            "Detects stereo mode"
        );
        Option qBitrate = repository.createOption(
            OptionTypes.SHORT,
            "q",
            2,
            ValueTypes.INTEGER,
            "Detects quality algorithm"
        );
        Option preset = repository.createOption(
            OptionTypes.LONG,
            "preset",
            "normal",
            ValueTypes.STRING,
            "Detects encoding preset"
        );

        try {
            repository.parse();
        }
        catch ( CannotParse ) {
            System.out.println("Cannot parse such commandline!\n");
            System.out.pirntln(repository.help());
        }

        System.out.println("vbrNew = " + (Boolean) vbrNew.getValue()); // vbrNew = true
        System.out.println("useReplayGain = " + (Boolean) useReplayGain.getValue()); // useReplayGain = true
        System.out.println("stereoMode = " + (Character) stereoMode.getValue()); // stereoMode = j
        System.out.println("qBitrate = " + (Integer) qBitrate.getValue()); // qBitrate = 0
        System.out.println("vBitrate = " + (Integer) vBitrate.getValue()); // vBitrate = 0
        System.out.println("preset = " + (String) preset.getValue()); // preset = normal

        Iterator<String> appArgs = repository.getApplicationArguments();
        System.out.println("inputFile is " + appArgs.next()); // inputFile is awesometune.wav
        System.out.println("outputFile is " + appArgs.next()); // outputFile is awesomefile.mp3
    }
}
