from google.colab import drive
drive.mount('/content/drive', force_remount=True)

import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder, StandardScaler
from keras import models, layers
from keras.models import load_model
import librosa
import traceback

def load_data(path_to_csv):
    data = pd.read_csv(path_to_csv)
    data = data.drop(labels='filename', axis=1)
    print("Features in dataset:", data.columns.tolist())
    return data

def preprocess_data(data):
    scaler = StandardScaler()
    feature_columns = data.columns.difference(['label'])
    data[feature_columns] = scaler.fit_transform(data[feature_columns])
    encoder = LabelEncoder()
    data['label'] = encoder.fit_transform(data['label'])
    return data, scaler, encoder, feature_columns

def split_data(data):
    X = data.drop('label', axis=1)
    y = data['label']
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    return X_train, X_test, y_train, y_test

def create_model(input_shape, num_classes):
    model = models.Sequential([
        layers.Dense(256, activation='relu', input_shape=(input_shape,)),
        layers.Dropout(0.3),
        layers.Dense(128, activation='relu'),
        layers.Dropout(0.3),
        layers.Dense(64, activation='relu'),
        layers.Dense(num_classes, activation='softmax')
    ])
    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
    return model

def train_and_evaluate(model, X_train, y_train, X_test, y_test):
    history = model.fit(X_train, y_train, epochs=50, batch_size=32, validation_data=(X_test, y_test))
    test_loss, test_acc = model.evaluate(X_test, y_test)
    print(f"Test Accuracy: {test_acc}")
    return history

def extract_features(audio_path):
  y, sr = librosa.load(audio_path, sr=None, mono=True,duration=30)

  features = {}

  features['length'] = len(y)

  chroma_stft = librosa.feature.chroma_stft(y=y, sr=sr)
  features['chroma_stft_mean'] = np.mean(chroma_stft)
  features['chroma_stft_var'] = np.var(chroma_stft)

  rms = librosa.feature.rms(y=y)
  features['rms_mean'] = np.mean(rms)
  features['rms_var'] = np.var(rms)

  spec_cent = librosa.feature.spectral_centroid(y=y, sr=sr)
  features['spectral_centroid_mean'] = np.mean(spec_cent)
  features['spectral_centroid_var'] = np.var(spec_cent)

  spec_bw = librosa.feature.spectral_bandwidth(y=y, sr=sr)
  features['spectral_bandwidth_mean'] = np.mean(spec_bw)
  features['spectral_bandwidth_var'] = np.var(spec_bw)

  rolloff = librosa.feature.spectral_rolloff(y=y, sr=sr)
  features['rolloff_mean'] = np.mean(rolloff)
  features['rolloff_var'] = np.var(rolloff)

  zcr = librosa.feature.zero_crossing_rate(y)
  features['zero_crossing_rate_mean'] = np.mean(zcr)
  features['zero_crossing_rate_var'] = np.var(zcr)

  harmony, perceptr = librosa.effects.hpss(y)
  features['harmony_mean'] = np.mean(harmony)
  features['harmony_var'] = np.var(harmony)
  features['perceptr_mean'] = np.mean(perceptr)
  features['perceptr_var'] = np.var(perceptr)

  onset_env = librosa.onset.onset_strength(y=y, sr=sr)
  features['tempo'] = librosa.feature.rhythm.tempo(onset_envelope=onset_env, sr=sr)[0]

  mfccs = librosa.feature.mfcc(y=y, sr=sr, n_mfcc=20)
  for i in range(1, 21):
      features[f'mfcc{i}_mean'] = np.mean(mfccs[i-1])
      features[f'mfcc{i}_var'] = np.var(mfccs[i-1])

  return features

def preprocess_and_predict(audio_path, scaler, encoder, model, feature_columns):
    features = extract_features(audio_path)
    features_df = pd.DataFrame([features], columns=feature_columns)
    features_df = scaler.transform(features_df)
    prediction = model.predict(features_df)
    predicted_genre = encoder.inverse_transform([np.argmax(prediction)])[0]
    return predicted_genre

def load_and_evaluate_individual(model_path, scaler, encoder, feature_columns):
    model = load_model(model_path)

    while True:
        try:
            audio_path = input("Enter the path to an audio file (or type 'exit' to quit): ")
            if audio_path.lower() == 'exit':
                break
            if not audio_path.lower().endswith(('.wav', '.mp3', '.flac')):
                print("Please enter a valid audio file path with a correct extension (.wav, .mp3, .flac).")
                continue
            predicted_genre = preprocess_and_predict(audio_path, scaler, encoder, model, feature_columns)
            print(f"Predicted Genre: {predicted_genre}")
        except Exception as e:
            print(f"An error occurred: {e}")
            traceback.print_exc()


def main():
    path_to_csv = '/content/drive/MyDrive/Data/features_30_sec.csv'
    model_path = '/content/drive/MyDrive/Models/Music_Genre_Classification.keras'
    data = load_data(path_to_csv)
    data, scaler, encoder, feature_columns = preprocess_data(data)
    X_train, X_test, y_train, y_test = split_data(data)
    model = create_model(X_train.shape[1], len(encoder.classes_))
    history = train_and_evaluate(model, X_train, y_train, X_test, y_test)
    model.save(model_path)
    load_and_evaluate_individual(model_path, scaler, encoder, feature_columns)

if __name__ == "__main__":
    main()
