import './App.css'
import {Route, Routes} from "react-router-dom";
import ForageWiki from "./forage/pages/forageWiki/ForageWiki.tsx";
import {useEffect, useState} from "react";
import {ForageWikiItem} from "./forage/types/ForageWikiItem.ts";
import axios from "axios";
import AdminDashboard from "./forage/pages/adminDashboard/AdminDashboard.tsx";
import {CustomMarker} from "./forage/types/CustomMarker.ts";
import MapComponent from "./forage/components/mapComponent/MapComponent.tsx";

function App() {

	const [forageWikiItems, setForageWikiItems] = useState<ForageWikiItem[]>([])
	const [customMarker, setCustomMarker] = useState<CustomMarker[]>([])

	function fetchWikiData() {
		axios.get("api/forageWikiItems")
			.then(results => setForageWikiItems(
				results.data.map(
                    (item: ForageWikiItem) => (
						{
							...item,
							uniqueForageItemId: item.id || crypto.randomUUID()
						}
					)
				)
			))
			.catch(error => console.log(error))
	}

	function fetchCustomMarkerData() {
		axios.get("/api/customMarkers")
			.then(results => setCustomMarker(results.data))
			.catch(error => console.log(error))
	}

	useEffect(() => {
		fetchWikiData()
		fetchCustomMarkerData()
	}, []);

	return (
		<>
			Forage Tracker - Share your Forage!
			<Routes>
				<Route path={"/"} element={
					<>
						<p>Map</p>
						<MapComponent />
					</>
				} />
				<Route path={"/wiki"} element={
					<>
						<p>Wiki</p>
						<ForageWiki forageWikiItems={forageWikiItems}/>
					</>
				} />
				<Route path={"/admin"} element={
					<>
						<p>Admin</p>
						<AdminDashboard forageWikiItems={forageWikiItems} customMarker={customMarker}/>
					</>
				} />
			</Routes>
		</>
	)
}

export default App
