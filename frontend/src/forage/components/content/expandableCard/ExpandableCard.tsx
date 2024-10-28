import {ExpandableCardProps} from "./ExpandableCardProps.ts"
import {useState} from "react";
import axios from "axios";
import ForageWikiItemEditor from "../../../pages/forageWiki/forageWikiItemEditor/ForageWikiItemEditor.tsx";
import CustomMarkerEditor from "../../../pages/customMarkerGallery/customMarkerEditor/CustomMarkerEditor.tsx";

export default function ExpandableCard(props: Readonly<ExpandableCardProps>) {

    const [expanded, setExpanded] = useState<boolean>(false)
	const [editItem, setEditItem] = useState<boolean>(false)

	function deleteCustomMarker() {
		if (props.customMarker) {
			axios.delete("api/customMarkers/" + props.customMarker.id)
				.then(() => props.fetchCustomMarkerData?.())
		} else {
			console.log("No custom marker to delete")
		}
	}

	function deleteForageWikiItem() {
		if (props.forageWikiItem) {
			axios.delete("api/forageWikiItems/" + props.forageWikiItem.id)
				.then(() => props.fetchWikiData?.())
		} else {
			console.log("No forage wiki item to delete")
		}
	}

	return (
		<div className={"boxed boxed-r5 m-5 p-5"}>
            {props.customMarker &&
				(
					<div>
						{editItem ?
							<div>
								<CustomMarkerEditor fetchCustomMarkerData={props.fetchCustomMarkerData ?? (() => {})} customMarkerToEdit={props.customMarker} setEditItem={setEditItem}/>
							</div>
						:
							<div>
								<span>{props.customMarker.name}</span>
								<span>
								<button type={"button"} onClick={
									() => setExpanded(!expanded)}>
									{expanded ? "Hide" : "Show"}
								</button>
								</span>
								{expanded ?
									<div>
										<img src={props.customMarker.iconUrl} alt={props.customMarker.iconUrl}/>
										<p>
											Icon Size:
											{props.customMarker.iconSize[0]}px x {props.customMarker.iconSize[1]}px
										</p>
										<p>
											Icon Anchor:
											x-Offset: {props.customMarker.iconAnchor[0]}px,
											y-Offset: {props.customMarker.iconAnchor[1]}px
										</p>
										<p>
											Popup Anchor:
											x-Offset: {props.customMarker.popupAnchor[0]}px,
											y-Offset: {props.customMarker.popupAnchor[1]}px
										</p>
										<p>Popup Text: {props.customMarker.popupText}</p>
										<div className={"flex flex-row-reverse"}>
											<span>
												<button type={"button"}
														onClick={deleteCustomMarker}>Delete</button>
											</span>
											<span>
												<button type={"button"} onClick={() => {
													if (props.customMarker) {
														setEditItem(true)
													}
												}}>Edit</button>
											</span>
										</div>
									</div>
									:
									null
								}
							</div>
						}
					</div>
				)
			}
			{props.forageWikiItem &&
				(
					<div>
						{editItem ?
							<div>
								<ForageWikiItemEditor fetchWikiData={props.fetchWikiData ?? (() => {
								})} forageWikiItemToEdit={props.forageWikiItem} setEditItem={setEditItem}/>
							</div>
							:
							<div>
								<span>{props.forageWikiItem.name}</span>
								<span>
								<button type={"button"} onClick={() => setExpanded(!expanded)}>{expanded ? "Hide" : "Show"}</button>
								</span>
								{expanded ?
									<div>
											<div>
												<p>Category: {props.forageWikiItem.category}</p>
												<p>Source: {props.forageWikiItem.source}</p>
												<p>Description: {props.forageWikiItem.description}</p>
												<p>Season: {props.forageWikiItem.season}</p>
												<div className={"flex flex-row-reverse"}>
													<span>
														<button type={"button"} onClick={deleteForageWikiItem}>Delete</button>
													</span>
													<span>
														<button type={"button"} onClick={() => {
															if (props.forageWikiItem) {
																setEditItem(true)
															}
														}}>Edit</button>
													</span>
												</div>
											</div>
									</div>
								:
									null
								}
							</div>
						}
                	</div>
				)
			}
		</div>
	)
}