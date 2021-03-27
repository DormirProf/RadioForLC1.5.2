package javazoom.jl.decoder;

import javazoom.jl.decoder.DecoderException;

public interface FrameDecoder {

   void decodeFrame() throws DecoderException;
}
