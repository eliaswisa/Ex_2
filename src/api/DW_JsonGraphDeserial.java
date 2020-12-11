package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Iterator;

public class DW_JsonGraphDeserial implements JsonDeserializer<directed_weighted_graph> {
    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        directed_weighted_graph DWgraph = new DWGraph_DS();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        //getting all "nodes" objects from json file as an array
        JsonArray jsonArray = (JsonArray) jsonObject.get("Nodes");
        //inserting to iterator
        Iterator<JsonElement> iterat = jsonArray.iterator();
        while (iterat.hasNext()) {
            JsonElement tempElement = iterat.next();
            //creating new nodes by  id from temp elements
            int id = tempElement.getAsJsonObject().get("id").getAsInt();
            node_data n = new NodeData(id);
//take all positions of every node as string.spliting if there is psik for every axis (x,y,z)
            String pos = tempElement.getAsJsonObject().get("pos").getAsString();
            String[] posSplit = pos.split(",");
            double x = Double.parseDouble(posSplit[0]);
            double y = Double.parseDouble(posSplit[1]);
            double z = Double.parseDouble(posSplit[2]);
            //implementing the node geo location for specific node
            geo_location geoLoc = new GeoLocation(x, y, z);
            //sets the location to the node
            n.setLocation(geoLoc);
//adding the node to the graph
            DWgraph.addNode(n);
        }
//doing the same thing like nodes but just with edges and their fields
        //running on all the edges and take one by one every time with all their fields  and connect betweeen nodes
        JsonArray jsonEdgesArray = (JsonArray) jsonObject.get("Edges");
        Iterator<JsonElement> it2 = jsonEdgesArray.iterator();
        while (it2.hasNext()) {
            JsonElement tempEdge = it2.next();
            int src = tempEdge.getAsJsonObject().get("src").getAsInt();
            double w = tempEdge.getAsJsonObject().get("w").getAsDouble();
            int dest = tempEdge.getAsJsonObject().get("dest").getAsInt();
            DWgraph.connect(src, dest, w);
        }

        return DWgraph;
    }


}
