package com.sqvat.squat;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sqvat.squat.data.Session;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrackSessionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrackSessionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TrackSessionFragment extends Fragment {

    private Session session;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param session
     * @return A new instance of fragment TrackSessionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackSessionFragment newInstance(Session session) {
        TrackSessionFragment fragment = new TrackSessionFragment();
        Bundle args = new Bundle();
        args.putInt("sessionId", session.getId().intValue());

        fragment.setArguments(args);
        return fragment;
    }
    public TrackSessionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            session = Session.load(Session.class, getArguments().getInt("sessionId"));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_track_session, container, false);

        return view;

    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
