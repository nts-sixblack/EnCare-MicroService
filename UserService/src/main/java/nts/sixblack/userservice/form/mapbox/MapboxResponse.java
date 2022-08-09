package nts.sixblack.userservice.form.mapbox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapboxResponse {
    private List<Route> routes;
    private List<Point> waypoints;
    private String code;
    private String uuid;
}
