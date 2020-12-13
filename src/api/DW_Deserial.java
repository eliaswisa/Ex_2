package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Iterator;

/***
 *Abstract class that defines API used by ObjectMapper (and other chained JsonDeserializers too) to deserialize Objects of arbitrary types from JSON, using provided JsonParser.
 */
public class DW_Deserial implements JsonDeserializer<directed_weighted_graph> {
    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        directed_weighted_graph graph = new DWGraph_DS();

        // Iterating over the Nodes json array to create the nodes of the graph and set their location
        JsonArray jsonArray= (JsonArray)jsonObject.get("Nodes");
        Iterator<JsonElement> it= jsonArray.iterator();
        while (it.hasNext()){
            JsonElement temp= it.next();
            int id=temp.getAsJsonObject().get("id").getAsInt();
            node_data n= new NodeData(id);

            String pos=temp.getAsJsonObject().get("pos").getAsString();
            String[] posSplit= pos.split(",");
            double x= Double.parseDouble(posSplit[0]);
            double y= Double.parseDouble(posSplit[1]);
            double z= Double.parseDouble(posSplit[2]);
            geo_location geoLoc= new GeoLocation(x,y,z);
            n.setLocation(geoLoc);

            graph.addNode(n);
        }

        // Iterating over the Edges json array to create all the edges of the graph with the right weight.
        JsonArray jsonArrayEdge= (JsonArray)jsonObject.get("Edges");
        Iterator<JsonElement> it2= jsonArrayEdge.iterator();
        while (it2.hasNext()){
            JsonElement temp= it2.next();
            int src=temp.getAsJsonObject().get("src").getAsInt();
            double w= temp.getAsJsonObject().get("w").getAsDouble();
            int dest=temp.getAsJsonObject().get("dest").getAsInt();
            graph.connect(src,dest,w);
        }

        return graph;
    }


}

