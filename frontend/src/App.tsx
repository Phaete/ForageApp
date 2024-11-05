import {Route, Routes} from "react-router-dom";
import ForageWiki from "./forage/pages/forageWiki/ForageWiki.tsx";
import {useEffect, useState} from "react";
import {ForageWikiItem} from "./forage/types/ForageWikiItem.ts";
import axios from "axios";
import AdminDashboard from "./forage/pages/adminDashboard/AdminDashboard.tsx";
import {CustomMarker} from "./forage/types/CustomMarker.ts";
import Navbar from "./forage/components/navbar/Navbar.tsx";
import Content from "./forage/components/content/Content.tsx";
import LandingPage from "./forage/pages/landingPage/LandingPage.tsx";
import MapView from "./forage/pages/mapView/MapView.tsx";
import {ForageMapItem} from "./forage/types/ForageMapItem.ts";
import Footer from "./forage/components/footer/Footer.tsx";

function App() {

	const [forageWikiItems, setForageWikiItems] = useState<ForageWikiItem[]>([])
	const [customMarker, setCustomMarker] = useState<CustomMarker[]>([])
	const [forageMapItems, setForageMapItems] = useState<ForageMapItem[]>([])

	function fetchWikiData() {
		axios.get("api/forageWikiItems")
			.then(results => setForageWikiItems(results.data))
			.catch(error => console.log(error))
	}

	function fetchCustomMarkerData() {
		axios.get("/api/customMarkers")
			.then(results => setCustomMarker(results.data))
			.catch(error => console.log(error))
	}

	function fetchForageMapItems() {
		axios.get("/api/forageMapItems")
			.then(results => setForageMapItems(results.data["true"]))
			.catch(error => console.log(error))
	}

	useEffect(() => {
		fetchWikiData()
		fetchCustomMarkerData()
		fetchForageMapItems()
	}, []);

	return (
		<div className={"full-size flex flex-col justify-center align-center"}>
			<Navbar />
			<Content>
				<Routes>
					<Route path={"/"} element={
							<LandingPage />
					} />
					<Route path={"/map"} element={
							<MapView customMarker={customMarker} forageWikiItems={forageWikiItems} forageMapItems={forageMapItems} fetchForageMapItems={fetchForageMapItems}/>
					} />
					<Route path={"/wiki"} element={
							<ForageWiki forageWikiItems={forageWikiItems} fetchWikiData={fetchWikiData}/>
					} />
					<Route path={"/admin"} element={
							<AdminDashboard forageWikiItems={forageWikiItems} customMarkers={customMarker} fetchWikiData={fetchWikiData} fetchCustomMarkerData={fetchCustomMarkerData}/>
					} />
				</Routes>
			</Content>
			<Footer />
		</div>
	)
}

export default App
