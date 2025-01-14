import React from 'react';
import Test from './Components/Test/Test';
import Header from './Components/Header/Header';
import Footer from './Components/Footer/Footer';
import GridStoreList from './Components/GridStoreList/GridStoreList';
import Dashboard from './Pages/Dashboard';

 const App = () => {
  return (
    <div className="App">
      <Header/>
      <Dashboard/>
      <Footer/>
    </div>
  );
}

export default App;
