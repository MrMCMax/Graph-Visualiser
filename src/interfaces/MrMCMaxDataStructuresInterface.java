package interfaces;

import java.io.IOException;

import mrmcmax.data_structures.graphs.Graph;
import mrmcmax.persistence.IGraphReader;
import mrmcmax.persistence.TikZReader;

public class MrMCMaxDataStructuresInterface {

	public Graph loadGraph(String path) throws IOException{
		IGraphReader reader = new TikZReader();
		return reader.loadGraphAbsolute(path);
	}
}
