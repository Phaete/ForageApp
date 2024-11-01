import L from 'leaflet';
import {CustomMarkerMapComponentProps} from "./CustomMarkerMapComponentProps.ts";
import {Marker, Popup} from "react-leaflet";
import {useEffect, useState} from "react";

const CustomMarkerMapComponent = (props: Readonly<CustomMarkerMapComponentProps>) => {

    const [customIcon, setCustomIcon] = useState<L.Icon>(
        L.icon({
            iconUrl: props.forageMapItem.customMarker.iconUrl,
            iconSize: [props.forageMapItem.customMarker.iconSize[0], props.forageMapItem.customMarker.iconSize[1]],
            iconAnchor: [props.forageMapItem.customMarker.iconAnchor[0], props.forageMapItem.customMarker.iconAnchor[1]],
            popupAnchor: [props.forageMapItem.customMarker.popupAnchor[0], props.forageMapItem.customMarker.popupAnchor[1]]
        })
    )

    useEffect(() => {
        setCustomIcon(
            L.icon({
                iconUrl: props.forageMapItem.customMarker.iconUrl,
                iconSize: [
                    Math.min(props.forageMapItem.customMarker.iconSize[0]+((props.zoom-18)*3), 48),
                    Math.min(props.forageMapItem.customMarker.iconSize[1]+((props.zoom-18)*3), 48)
                ],
                iconAnchor: [
                    Math.min(props.forageMapItem.customMarker.iconAnchor[0]+(props.zoom-18)*1.5, 24),
                    Math.min(props.forageMapItem.customMarker.iconAnchor[1]+((props.zoom-18)*3), 48)
                ],
                popupAnchor: [
                    props.forageMapItem.customMarker.popupAnchor[0],
                    0
                ]
            })
        )
    }, [props.zoom]);

    return (
        <Marker position={[props.forageMapItem.position.latitude, props.forageMapItem.position.longitude]} icon={customIcon}>
            <Popup>
                <p>{props.forageMapItem.forageWikiItem.name}</p>
            </Popup>
        </Marker>
    )
}

export default CustomMarkerMapComponent