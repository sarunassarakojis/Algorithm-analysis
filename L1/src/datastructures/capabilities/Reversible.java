package datastructures.capabilities;

import java.io.IOException;

/**
 * Project: L1
 * <p>
 * Created by Šaras on 16/04/03.
 */
public interface Reversible {

    void undo() throws IOException;
}
