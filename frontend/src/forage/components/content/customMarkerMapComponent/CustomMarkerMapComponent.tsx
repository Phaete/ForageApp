import L from 'leaflet';
import {CustomMarkerMapComponentProps} from "./CustomMarkerMapComponentProps.ts";
import {Marker} from "react-leaflet";
import {useEffect, useState} from "react";

const CustomMarkerMapComponent = (props: Readonly<CustomMarkerMapComponentProps>) => {

    const [customIcon, setCustomIcon] = useState<L.Icon>(
        L.icon({
            iconUrl: props.forageMapItem.customMarker.iconUrl,
            iconSize: [props.forageMapItem.customMarker.iconSize[0]/2, props.forageMapItem.customMarker.iconSize[1]/2],
            iconAnchor: [props.forageMapItem.customMarker.iconAnchor[0], props.forageMapItem.customMarker.iconAnchor[1]],
            popupAnchor: [props.forageMapItem.customMarker.popupAnchor[0], props.forageMapItem.customMarker.popupAnchor[1]]
        })
    )

    useEffect(() => {
        setCustomIcon(
            L.icon({
                iconUrl: props.forageMapItem.customMarker.iconUrl,
                iconSize: [
                    ((24*props.zoom)/18)+4,
                    ((24*props.zoom)/18)+4
                ],
                iconAnchor: [
                    ((12*props.zoom)/9)+2,
                    ((24*props.zoom)/18)+4
                ],
                popupAnchor: [
                    0,
                    0
                ]
            })
        )
    }, [props.zoom]);

    return (
        <Marker
            position={[props.forageMapItem.position.latitude, props.forageMapItem.position.longitude]}
            icon={customIcon}
            eventHandlers={{click: () => {
                    props.setDetailedForageMapItem(props.forageMapItem)
                    console.log(props.forageMapItem)
                }
            }}>
        </Marker>
    )
}

export default CustomMarkerMapComponent