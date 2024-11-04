import L from 'leaflet';
import {CustomMarkerMapComponentProps} from "./CustomMarkerMapComponentProps.ts";
import {Marker, Popup} from "react-leaflet";
import {useEffect, useState} from "react";
import ForageMapItemEditor from "../../../pages/mapView/forageMapItemEditor/ForageMapItemEditor.tsx";
import ForageMapItemView from "../forageMapItemView/ForageMapItemView.tsx";

const CustomMarkerMapComponent = (props: Readonly<CustomMarkerMapComponentProps>) => {

    const [customIcon, setCustomIcon] = useState<L.Icon>(
        L.icon({
            iconUrl: props.forageMapItem.customMarker.iconUrl,
            iconSize: [props.forageMapItem.customMarker.iconSize[0]/2, props.forageMapItem.customMarker.iconSize[1]/2],
            iconAnchor: [props.forageMapItem.customMarker.iconAnchor[0], props.forageMapItem.customMarker.iconAnchor[1]],
            popupAnchor: [props.forageMapItem.customMarker.popupAnchor[0], props.forageMapItem.customMarker.popupAnchor[1]]
        })
    )
    const [isEditable, setIsEditable] = useState<boolean>(false)

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
        <Marker position={[props.forageMapItem.position.latitude, props.forageMapItem.position.longitude]} icon={customIcon}>
            <Popup>
                {isEditable ?
                    <ForageMapItemEditor
                        forageMapItemPosition={props.forageMapItem.position}
                        fetchForageMapItems={props.fetchForageMapItems}
                        forageWikiItems={props.forageWikiItems}
                        customMarker={props.customMarker}
                        setAddForageMapItem={setIsEditable}
                        forageMapItemToEdit={props.forageMapItem}/>
                :
                    <ForageMapItemView
                        forageMapItem={props.forageMapItem}
                        setIsEditable={setIsEditable}
                        fetchForageMapItems={props.fetchForageMapItems}/>
                }

            </Popup>
        </Marker>
    )
}

export default CustomMarkerMapComponent